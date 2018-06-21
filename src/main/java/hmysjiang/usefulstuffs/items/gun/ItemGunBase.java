package hmysjiang.usefulstuffs.items.gun;

import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.client.gui.GUIHandler;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.tileentity.capability.FilteredItemStackHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public abstract class ItemGunBase extends Item {
	
	public ItemGunBase() {
		this.setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
		if (playerIn.isSneaking() && hand == EnumHand.MAIN_HAND) {
			if (!playerIn.worldObj.isRemote) {
				playerIn.openGui(UsefulStuffs.instance, GUIHandler.GUI_GUN_INV, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
			}
		}
		else if (hasAmmo(itemStackIn) && !outputFull(itemStackIn)){
			launchAmmo(playerIn, itemStackIn);
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Cont")) {
			initializeHandler(stack);
		}
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		super.onCreated(stack, worldIn, playerIn);
		initializeHandler(stack);
	}

	protected void initializeHandler(ItemStack stack) {
		ItemStackHandler handler = new ItemStackHandler(4);
		NBTTagCompound compound = new NBTTagCompound();
		compound.setTag("Cont", handler.serializeNBT());
		stack.setTagCompound(compound);
	}
	
	protected boolean hasAmmo(ItemStack stack) {
		ItemStackHandler handler = new ItemStackHandler(4);
		handler.deserializeNBT(stack.getTagCompound().getCompoundTag("Cont"));
		if (handler.getStackInSlot(0) != null && handler.getStackInSlot(0).stackSize > 0)
			return true;
		return false;
	}
	
	protected boolean outputFull(ItemStack stack) {
		ItemStackHandler handler = new ItemStackHandler(4);
		handler.deserializeNBT(stack.getTagCompound().getCompoundTag("Cont"));
		if (handler.getStackInSlot(1) == null || handler.getStackInSlot(2) == null || handler.getStackInSlot(3) == null)
			return false;
		if (handler.getStackInSlot(1).stackSize == 64 && handler.getStackInSlot(2).stackSize == 64 && handler.getStackInSlot(3).stackSize == 64)
			return true;
		return false;
	}
	
	protected abstract void launchAmmo(EntityPlayer player, ItemStack stack);
	
}
