package hmysjiang.usefulstuffs.items;

import java.util.List;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.enchantment.EnchantmentXL;
import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.utils.helper.LogHelper;
import hmysjiang.usefulstuffs.utils.helper.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPackingGlue extends Item {
	
	public static int durability;
	
	public ItemPackingGlue() {
		setUnlocalizedName(Reference.ModItems.PACKING_GLUE.getUnlocalizedName());
		setRegistryName(Reference.ModItems.PACKING_GLUE.getRegistryName());
		setMaxStackSize(1);
		
		durability = ConfigManager.glueDurability;
		setMaxDamage(durability);
	}
	
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos,
			EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		if (!world.isRemote) {
			IBlockState state = world.getBlockState(pos);
			if (player.isSneaking() && state.getBlock() != ModBlocks.glued_box && state.getBlockHardness(world, pos) >= 0) {
				Block block = state.getBlock();
				for (String blacklistedBlock: ConfigManager.glueBlackList) {
					String[] metaSet = blacklistedBlock.split(" ");
					if (metaSet.length == 1) {
						if (blacklistedBlock.equals(block.getRegistryName().toString())) {
							player.sendMessage(new TextComponentString("This block is blacklisted in the config"));
							return EnumActionResult.PASS;
						}
					}
					else {
						try {
							if (metaSet[0].equals(block.getRegistryName().toString()) && block.getMetaFromState(block.getExtendedState(state, world, pos)) == Integer.parseInt(metaSet[1])) {
								player.sendMessage(new TextComponentString("This block is blacklisted in the config"));
								return EnumActionResult.PASS;
							}
						}catch (NumberFormatException exception) {
							LogHelper.error("Error occured while parsing data <" + blacklistedBlock + "> in usefulstuffs.cfg.");
						}
					}
				}
				ItemStack stack = player.getHeldItem(hand);
				float raw_cost = WorldHelper.getBlockDataDensity(world, pos, state, world.getTileEntity(pos));
				Integer enchLevel = ConfigManager.functionXl ? EnchantmentHelper.getEnchantments(stack).get(EnchantmentXL.INSTANCE) : null;
				if (enchLevel != null) {
					if (enchLevel == 1) {
						raw_cost /= 1.5;
					}
					else if (enchLevel == 2) {
						raw_cost /= 2.5;
					}
					else if (enchLevel == 3) {
						raw_cost /= 4;
					}	
				}
				int cost = MathHelper.ceil(raw_cost);
				if (stack.getMaxDamage() - stack.getItemDamage() >= cost) {
					stack.setItemDamage(stack.getItemDamage() + cost);
					ItemStack drop = new ItemStack(ModBlocks.glued_box, 1);
					NBTTagCompound compound = new NBTTagCompound();
					if (world.getTileEntity(pos) != null) {
						NBTTagCompound tileData = world.getTileEntity(pos).serializeNBT();
						tileData.removeTag("x");
						tileData.removeTag("y");
						tileData.removeTag("z");
						compound.setTag("Tile", tileData);
						world.removeTileEntity(pos);
					}
					compound.setInteger("Meta", block.getMetaFromState(state));
					compound.setString("Name", block.getRegistryName().toString());
					drop.setTagCompound(compound);
					world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, drop));
					world.setBlockToAir(pos);
					world.notifyNeighborsOfStateChange(pos, Blocks.AIR, false);
					return EnumActionResult.SUCCESS;
				}
				else if (stack.getMaxDamage() >= cost) {
					player.sendMessage(new TextComponentString("Insufficient durability! Required: " + cost + " Left: " + (stack.getMaxDamage() - stack.getItemDamage())));
				}
				else {
					player.sendMessage(new TextComponentString("You cannot pick this block up with this glue. Try to enchant this with Frugal !"));
				}
			}
		}
		return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
	}
	
	@Override
	public int getItemEnchantability() {
		return 2;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("usefulstuffs.packing_glue.tooltip_1"));
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.packing_glue.tooltip_2"));
		tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.packing_glue.tooltip_3"));
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment == EnchantmentXL.INSTANCE;
	}
	
}
