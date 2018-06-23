package hmysjiang.usefulstuffs.items;

import java.util.List;

import hmysjiang.usefulstuffs.miscs.helper.InventoryHelper;
import hmysjiang.usefulstuffs.miscs.helper.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemBuildingWand extends Item {
	
	public ItemBuildingWand(String unloacalizedName, String registryName) {
		setUnlocalizedName(unloacalizedName);
		setRegistryName(registryName);
		
		if (!(this instanceof ItemBuildingWandInfinite)) {
			setMaxDamage(16 * 128);
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
		Object[] ret = WorldHelper.getBlockPosFacingEntityLookingAt(playerIn, itemStackIn.getItem() instanceof ItemBuildingWandInfinite ? 32 : 16);
		if (ret != null && ret[1] != null) {
			BlockPos pos = (BlockPos) ret[0];
			Vec3d p = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
			Vec3d e = null;
			IBlockState state = worldIn.getBlockState(pos);
			EnumFacing facing = (EnumFacing) ret[1];
			ItemStack stackTemplate = playerIn.getHeldItemOffhand();
			if (stackTemplate != null && stackTemplate.getItem() instanceof ItemBlock && stackTemplate.stackSize > 0) {
				ItemStack stack = InventoryHelper.findStackInPlayerInventory(playerIn, stackTemplate);
				Block block = ((ItemBlock)stackTemplate.getItem()).getBlock();
				boolean flag = block == state.getBlock();
				boolean flag1 = true;
				switch(facing) {
				case WEST:
					e = new Vec3d(-1, 0, 0);
					while (flag1) {
						p = p.add(e);
						if (!(worldIn.getBlockState(new BlockPos(p)).getMaterial().isReplaceable() || worldIn.getBlockState(new BlockPos(p)).getBlock() == Blocks.AIR) || (p.xCoord + 0.5 - playerIn.posX) < 1)
							break;
						worldIn.setBlockState(new BlockPos(p), flag ? state : block.getStateFromMeta(stackTemplate.getMetadata()));
						worldIn.playSound(playerIn, new BlockPos(p), block.getSoundType(block.getDefaultState(), worldIn, new BlockPos(p), playerIn).getPlaceSound(), SoundCategory.BLOCKS, 1, 1);
						if (!playerIn.capabilities.isCreativeMode) {
							stack.stackSize--;
							if (stack.stackSize == 0) {
								playerIn.inventory.deleteStack(stack);
								if (stack.equals(stackTemplate)) {
									flag1 = false;
								}
								else {
									stack = InventoryHelper.findStackInPlayerInventory(playerIn, stackTemplate);
									if (stack == null){
										flag1 = false;
									}
								}
							}
							if (!(itemStackIn.getItem() instanceof ItemBuildingWandInfinite)) {
								itemStackIn.setItemDamage(itemStackIn.getItemDamage() + 1);
								if (itemStackIn.getItemDamage() == itemStackIn.getMaxDamage()) {
									playerIn.inventory.deleteStack(itemStackIn);
									flag1 = false;
								}
							}
						}
					}
					break;
				case EAST:
					e = new Vec3d(1, 0, 0);
					while (flag1) {
						p = p.add(e);
						if (!(worldIn.getBlockState(new BlockPos(p)).getMaterial().isReplaceable() || worldIn.getBlockState(new BlockPos(p)).getBlock() == Blocks.AIR) || (playerIn.posX - (p.xCoord + 0.5)) < 1)
							break;
						worldIn.setBlockState(new BlockPos(p), flag ? state : block.getStateFromMeta(stackTemplate.getMetadata()));
						worldIn.playSound(playerIn, new BlockPos(p), block.getSoundType(block.getDefaultState(), worldIn, new BlockPos(p), playerIn).getPlaceSound(), SoundCategory.BLOCKS, 1, 1);
						if (!playerIn.capabilities.isCreativeMode) {
							stack.stackSize--;
							if (stack.stackSize == 0) {
								playerIn.inventory.deleteStack(stack);
								if (stack.equals(stackTemplate)) {
									flag1 = false;
								}
								else {
									stack = InventoryHelper.findStackInPlayerInventory(playerIn, stackTemplate);
									if (stack == null){
										flag1 = false;
									}
								}
							}
							if (!(itemStackIn.getItem() instanceof ItemBuildingWandInfinite)) {
								itemStackIn.setItemDamage(itemStackIn.getItemDamage() + 1);
								if (itemStackIn.getItemDamage() == itemStackIn.getMaxDamage()) {
									playerIn.inventory.deleteStack(itemStackIn);
									flag1 = false;
								}
							}
						}
					}
					break;
				case NORTH:
					e = new Vec3d(0, 0, -1);
					while (flag1) {
						p = p.add(e);
						if (!(worldIn.getBlockState(new BlockPos(p)).getMaterial().isReplaceable() || worldIn.getBlockState(new BlockPos(p)).getBlock() == Blocks.AIR) || (p.zCoord + 0.5 - playerIn.posZ) < 1)
							break;
						worldIn.setBlockState(new BlockPos(p), flag ? state : block.getStateFromMeta(stackTemplate.getMetadata()));
						worldIn.playSound(playerIn, new BlockPos(p), block.getSoundType(block.getDefaultState(), worldIn, new BlockPos(p), playerIn).getPlaceSound(), SoundCategory.BLOCKS, 1, 1);
						if (!playerIn.capabilities.isCreativeMode) {
							stack.stackSize--;
							if (stack.stackSize == 0) {
								playerIn.inventory.deleteStack(stack);
								if (stack.equals(stackTemplate)) {
									flag1 = false;
								}
								else {
									stack = InventoryHelper.findStackInPlayerInventory(playerIn, stackTemplate);
									if (stack == null){
										flag1 = false;
									}
								}
							}
							if (!(itemStackIn.getItem() instanceof ItemBuildingWandInfinite)) {
								itemStackIn.setItemDamage(itemStackIn.getItemDamage() + 1);
								if (itemStackIn.getItemDamage() == itemStackIn.getMaxDamage()) {
									playerIn.inventory.deleteStack(itemStackIn);
									flag1 = false;
								}
							}
						}
					}
					break;
				case SOUTH:
					e = new Vec3d(0, 0, 1);
					while (flag1) {
						p = p.add(e);
						if (!(worldIn.getBlockState(new BlockPos(p)).getMaterial().isReplaceable() || worldIn.getBlockState(new BlockPos(p)).getBlock() == Blocks.AIR) || (playerIn.posZ - (p.zCoord + 0.5)) < 1)
							break;
						worldIn.setBlockState(new BlockPos(p), flag ? state : block.getStateFromMeta(stackTemplate.getMetadata()));
						worldIn.playSound(playerIn, new BlockPos(p), block.getSoundType(block.getDefaultState(), worldIn, new BlockPos(p), playerIn).getPlaceSound(), SoundCategory.BLOCKS, 1, 1);
						if (!playerIn.capabilities.isCreativeMode) {
							stack.stackSize--;
							if (stack.stackSize == 0) {
								playerIn.inventory.deleteStack(stack);
								if (stack.equals(stackTemplate)) {
									flag1 = false;
								}
								else {
									stack = InventoryHelper.findStackInPlayerInventory(playerIn, stackTemplate);
									if (stack == null){
										flag1 = false;
									}
								}
							}
							if (!(itemStackIn.getItem() instanceof ItemBuildingWandInfinite)) {
								itemStackIn.setItemDamage(itemStackIn.getItemDamage() + 1);
								if (itemStackIn.getItemDamage() == itemStackIn.getMaxDamage()) {
									playerIn.inventory.deleteStack(itemStackIn);
									flag1 = false;
								}
							}
						}
					}
					break;
				case UP:
					e = new Vec3d(0, 1, 0);
					while (flag1) {
						p = p.add(e);
						if (!(worldIn.getBlockState(new BlockPos(p)).getMaterial().isReplaceable() || worldIn.getBlockState(new BlockPos(p)).getBlock() == Blocks.AIR) || (playerIn.posY - (p.yCoord + 0.5)) < 1)
							break;
						worldIn.setBlockState(new BlockPos(p), flag ? state : block.getStateFromMeta(stackTemplate.getMetadata()));
						worldIn.playSound(playerIn, new BlockPos(p), block.getSoundType(block.getDefaultState(), worldIn, new BlockPos(p), playerIn).getPlaceSound(), SoundCategory.BLOCKS, 1, 1);
						if (!playerIn.capabilities.isCreativeMode) {
							stack.stackSize--;
							if (stack.stackSize == 0) {
								playerIn.inventory.deleteStack(stack);
								if (stack.equals(stackTemplate)) {
									flag1 = false;
								}
								else {
									stack = InventoryHelper.findStackInPlayerInventory(playerIn, stackTemplate);
									if (stack == null){
										flag1 = false;
									}
								}
							}
							if (!(itemStackIn.getItem() instanceof ItemBuildingWandInfinite)) {
								itemStackIn.setItemDamage(itemStackIn.getItemDamage() + 1);
								if (itemStackIn.getItemDamage() == itemStackIn.getMaxDamage()) {
									playerIn.inventory.deleteStack(itemStackIn);
									flag1 = false;
								}
							}
						}
					}
					break;
				case DOWN:
					e = new Vec3d(0, -1, 0);
					while (flag1) {
						p = p.add(e);
						if (!(worldIn.getBlockState(new BlockPos(p)).getMaterial().isReplaceable() || worldIn.getBlockState(new BlockPos(p)).getBlock() == Blocks.AIR) || (p.yCoord + 0.5 - playerIn.posY) < 2)
							break;
						worldIn.setBlockState(new BlockPos(p), flag ? state : block.getStateFromMeta(stackTemplate.getMetadata()));
						worldIn.playSound(playerIn, new BlockPos(p), block.getSoundType(block.getDefaultState(), worldIn, new BlockPos(p), playerIn).getPlaceSound(), SoundCategory.BLOCKS, 1, 1);
						if (!playerIn.capabilities.isCreativeMode) {
							stack.stackSize--;
							if (stack.stackSize == 0) {
								playerIn.inventory.deleteStack(stack);
								if (stack.equals(stackTemplate)) {
									flag1 = false;
								}
								else {
									stack = InventoryHelper.findStackInPlayerInventory(playerIn, stackTemplate);
									if (stack == null){
										flag1 = false;
									}
								}
							}
							if (!(itemStackIn.getItem() instanceof ItemBuildingWandInfinite)) {
								itemStackIn.setItemDamage(itemStackIn.getItemDamage() + 1);
								if (itemStackIn.getItemDamage() == itemStackIn.getMaxDamage()) {
									playerIn.inventory.deleteStack(itemStackIn);
									flag1 = false;
								}
							}
						}
					}
					break;
				}	
			}
			
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(I18n.format("usefulstuffs.building_wand.tooltip_1"));
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.building_wand.tooltip_2"));
		if (stack.getItem() instanceof ItemBuildingWandInfinite) {
			tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.building_wand.tooltip_infinite", TextFormatting.GRAY));
		}
		else {
			tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.building_wand.tooltip_normal"));
		}
	}
	
}
