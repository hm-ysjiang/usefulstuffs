package hmysjiang.usefulstuffs.items;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.enchantment.EnchantmentXL;
import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.utils.helper.WorldHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemPackingGlue extends Item {
	
	public final int DURABILITY = 32;
	
	public ItemPackingGlue() {
//		super(EnumHelper.addToolMaterial(Reference.MOD_ID + ":packing_glue", 0, 32, 0, 0, 3), new HashSet<Block>());
		setUnlocalizedName(Reference.ModItems.PACKING_GLUE.getUnlocalizedName());
		setRegistryName(Reference.ModItems.PACKING_GLUE.getRegistryName());
		setMaxStackSize(1);
		setMaxDamage(DURABILITY);
	}
	
	@Override
	public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
			EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		if (!world.isRemote) {
			IBlockState state = world.getBlockState(pos);
			if (player.isSneaking() && state.getBlock() != ModBlocks.glued_box && state.getBlockHardness(world, pos) >= 0) {
				float raw_cost = WorldHelper.getBlockDataDensity(world, pos, state, world.getTileEntity(pos));
				Integer enchLevel = EnchantmentHelper.getEnchantments(stack).get(EnchantmentXL.instance);
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
					compound.setInteger("Meta", (state.getBlock()).getMetaFromState(state));
					compound.setString("Name", state.getBlock().getRegistryName().toString());
					drop.setTagCompound(compound);
					world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, drop));
					world.setBlockToAir(pos);
				}
				else {
					player.sendMessage(new TextComponentString("Insufficient durability! Required: " + cost));
				}
			}
		}
		return super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ, hand);
	}
	
	@Override
	public int getItemEnchantability() {
		return 10;
	}
	
}
