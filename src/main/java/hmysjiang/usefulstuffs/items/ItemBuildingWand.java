package hmysjiang.usefulstuffs.items;

import java.util.List;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.utils.helper.InventoryHelper;
import hmysjiang.usefulstuffs.utils.helper.LogHelper;
import hmysjiang.usefulstuffs.utils.helper.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBuildingWand extends Item {
	
	public static int durability;
	public static int range;
	public static int rangeInfi;
	
	public ItemBuildingWand() {
		this(Reference.ModItems.BUILDING_WAND.getUnlocalizedName(), Reference.ModItems.BUILDING_WAND.getRegistryName());
	}
	
	public ItemBuildingWand(String unloacalizedName, String registryName) {
		setUnlocalizedName(unloacalizedName);
		setRegistryName(registryName);
		setMaxStackSize(1);
		
		durability = ConfigManager.buildingwandDurability;
		range = ConfigManager.buildingwandRange;
		rangeInfi = ConfigManager.buildingwandRangeInfinite;
		
		if (!(this instanceof ItemBuildingWandInfinite)) {
			setMaxDamage(durability);
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (!worldIn.isRemote) {
			RayTraceResult raytrace = WorldHelper.rayTrace(playerIn, playerIn.getHeldItem(hand).getItem() instanceof ItemBuildingWandInfinite ? rangeInfi : range);
			if (raytrace != null && raytrace.typeOfHit == Type.BLOCK) {
				ItemStack stackTemplate = playerIn.getHeldItemOffhand();
				if (!stackTemplate.isEmpty() && stackTemplate.getItem() instanceof ItemBlock) {
					ItemStack wand = playerIn.getHeldItemMainhand();
					ItemStack stack = InventoryHelper.findStackInPlayerInventory(playerIn, stackTemplate, false);
					Block block = ((ItemBlock)stackTemplate.getItem()).getBlock();
					BlockPos pos = raytrace.getBlockPos();
					BlockPos end = new BlockPos(playerIn);
					float hitX = (float) (raytrace.hitVec.x - pos.getX());
					float hitY = (float) (raytrace.hitVec.y - pos.getY());
					float hitZ = (float) (raytrace.hitVec.z - pos.getZ());
					EnumFacing facing = raytrace.sideHit;
					IBlockState state = block.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, stackTemplate.getMetadata(), playerIn, hand);
					boolean loop = true;
					while (loop) {
						pos = pos.offset(facing);
						if (!block.canPlaceBlockAt(worldIn, pos) || !(worldIn.getBlockState(pos).getMaterial().isReplaceable() || worldIn.getBlockState(pos).getBlock() == Blocks.AIR) || WorldHelper.getDistanceProjctedOnAxis(pos, end, facing.getAxis()) <= 0)
							break;
						worldIn.setBlockState(pos, state);
						block.onBlockPlacedBy(worldIn, pos, state, playerIn, stack);
						worldIn.playSound(playerIn, pos, block.getSoundType(block.getDefaultState(), worldIn, pos, playerIn).getPlaceSound(), SoundCategory.BLOCKS, 1, 1);
						if (!playerIn.capabilities.isCreativeMode) {
							stack.setCount(stack.getCount() - 1);
							if (stack.isEmpty()) {
								playerIn.inventory.deleteStack(stack);
								if (stack.equals(stackTemplate)) {
									loop = false;
								}
								else {
									stack = InventoryHelper.findStackInPlayerInventory(playerIn, stackTemplate, false);
									if (stack.isEmpty()){
										loop = false;
									}
								}
							}
							if (!(wand.getItem() instanceof ItemBuildingWandInfinite)) {
								wand.setItemDamage(wand.getItemDamage() + 1);
								if (wand.getItemDamage() == wand.getMaxDamage()) {
									playerIn.inventory.deleteStack(wand);
									loop = false;
								}
							}
						}
					}
					return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, wand);
				}
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(hand));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(I18n.format("usefulstuffs.building_wand.tooltip_1"));
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.building_wand.tooltip_2"));
		if (stack.getItem() instanceof ItemBuildingWandInfinite) {
			tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.building_wand.tooltip_infinite", rangeInfi, TextFormatting.GRAY));
		}
		else {
			tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.building_wand.tooltip_normal", range));
		}
	}
	
}