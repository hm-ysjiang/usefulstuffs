package hmysjiang.usefulstuffs.container;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public abstract class ContainerItem extends ContainerBase {
	
	protected static ItemStackHandler getDeserializedHandler(ItemStack stack, int size) {
		ItemStackHandler handler = new ItemStackHandler(size);
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Cont"))
			handler.deserializeNBT(stack.getTagCompound().getCompoundTag("Cont"));
		return handler;
	}
	
	protected ItemStack stack;
	protected int size;
	protected int blocked = -1;
	
	public ContainerItem(IInventory inv, ItemStack stack, int size) {
		super(inv, getDeserializedHandler(stack, size));
		this.stack = stack;
		this.size = size;
		if (inv instanceof InventoryPlayer) 
			blocked = size + 27 + ((InventoryPlayer)inv).currentItem;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Cont")) {
			NBTTagCompound compound = new NBTTagCompound();
			stack.setTagCompound(compound);
		}
		stack.getTagCompound().setTag("Cont", handler.serializeNBT());
	}
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		if (slotId  <0 || slotId > inventorySlots.size())
			return super.slotClick(slotId, dragType, clickTypeIn, player);
		if (slotId == blocked)
			return inventorySlots.get(slotId).getStack();
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}

}