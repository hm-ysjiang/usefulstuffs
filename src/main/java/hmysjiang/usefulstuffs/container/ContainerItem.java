package hmysjiang.usefulstuffs.container;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public abstract class ContainerItem extends Container {
	
	protected IInventory inv;
	protected ItemStack stack;
	protected int size;
	protected ItemStackHandler handler;
	protected int blocked = -1;
	
	public ContainerItem(IInventory inv, ItemStack stack, int size) {
		this.inv = inv;
		this.stack = stack;
		this.size = size;
		this.handler = new ItemStackHandler(size);
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Cont"))
			handler.deserializeNBT(stack.getTagCompound().getCompoundTag("Cont"));
		if (inv instanceof InventoryPlayer) 
			blocked = size + 27 + ((InventoryPlayer)inv).currentItem;
	}

	@Override
	public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < size) {
            	if (!this.mergeItemStack(itemstack1, size, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, size, false)) {
            	return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0)
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Cont"))
			return;
		stack.getTagCompound().setTag("Cont", handler.serializeNBT());
	}
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		if (slotId<0 || slotId>inventorySlots.size())
			return super.slotClick(slotId, dragType, clickTypeIn, player);
		if (slotId == blocked)
			return inventorySlots.get(slotId).getStack();
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}

}