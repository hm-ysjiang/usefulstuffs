package hmysjiang.usefulstuffs.items.baubles;

import java.util.List;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.potion.PotionLily;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemLilyBelt extends Item implements IBauble {

	public ItemLilyBelt() {
		setUnlocalizedName(Reference.ModItems.BELT_LILY.getUnlocalizedName());
		setRegistryName(Reference.ModItems.BELT_LILY.getRegistryName());
		setMaxStackSize(1);
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.BELT;
	}
	
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (player != null && player.getActivePotionEffect(PotionLily.instance) == null) {
			if (!player.isSneaking()) {
				BlockPos entityPos = new BlockPos(player);
				Block blockUnder = player.world.getBlockState(new BlockPos(entityPos.getX(), entityPos.getY()-1, entityPos.getZ())).getBlock();
				Block blockFeet = player.world.getBlockState(entityPos).getBlock();
				Block blockHead = player.world.getBlockState(new BlockPos(entityPos.getX(), entityPos.getY()+1, entityPos.getZ())).getBlock();
				if (!(blockFeet instanceof BlockLiquid && blockHead instanceof BlockLiquid)){
					if (blockFeet instanceof BlockLiquid) {
						if (player.posY%1 > 0.75) {
							player.motionY = 0;
							player.onGround = true;
							player.fallDistance = 0;
							player.extinguish();
						}
						else if (player.posY%1 > 0.55) {
							player.motionY = 0.05;
						}
					}
				}
			}
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		IBaublesItemHandler handler = playerIn.getCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null);
		ItemStack stack1 = playerIn.getHeldItem(handIn);
		playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, handler.getStackInSlot(3));
		handler.setStackInSlot(3, stack1);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("usefulstuffs.lily_belt.tooltip"));
	}
	
}
