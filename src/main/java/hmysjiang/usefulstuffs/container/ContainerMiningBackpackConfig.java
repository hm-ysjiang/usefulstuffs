package hmysjiang.usefulstuffs.container;

import hmysjiang.usefulstuffs.items.baubles.ItemBackpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMiningBackpackConfig extends Container {
	
	protected EntityPlayer player;
	protected IInventory inv;
	protected ItemStack stack;
	protected ItemStackHandler filter;
	protected int blocked = -1;
	public ContainerMiningBackpackConfig(EntityPlayer player, ItemStack stack) {
		this.player = player;
		this.inv = player.inventory;
		this.stack = stack;
		this.filter = new ItemStackHandler(18);
		filter.deserializeNBT(stack.getTagCompound().getCompoundTag("Filter"));
		if (inv instanceof InventoryPlayer) 
			blocked = 18 + 27 + ((InventoryPlayer)inv).currentItem;
		
		int xH = 8, yH = 27;

		for (int x = 0 ; x < 9 ; x++) {
			this.addSlotToContainer(new SlotItemHandler(filter, x, xH + x * 18, yH));
		}
		yH = 58;
		for (int x = 0 ; x < 9 ; x++) {
			this.addSlotToContainer(new SlotItemHandler(filter, x + 9, xH + x * 18, yH));
		}

		
		int xPos = 8;
		int yPos = 115;
		
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(inv, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
			}
		}
		
		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(inv, x, xPos + x * 18, yPos + 58));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean canDragIntoSlot(Slot slotIn) {
		if (slotIn instanceof SlotItemHandler) return false;
		return true;
	}
	
	@Override
	public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
		if (slotIn instanceof SlotItemHandler) return false;
		return true;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Filter")) {
			NBTTagCompound compound = new NBTTagCompound();
			stack.setTagCompound(compound);
		}
		stack.getTagCompound().setTag("Filter", filter.serializeNBT());
	}
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		if (slotId >=0 && slotId < 18) {
			if (!(player.inventory.getItemStack().getItem() instanceof ItemBackpack)) {
				ItemStack stack = player.inventory.getItemStack().copy();
				stack.setCount(1);
				if (stack.isItemDamaged())
					stack.setItemDamage(0);
				filter.setStackInSlot(slotId, stack);
				return ItemStack.EMPTY;	
			}
		}
		if (slotId == blocked)
			return inventorySlots.get(slotId).getStack();
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}
	
	public ItemStack getStack() {
		return stack;
	}
	
	public EntityPlayer getPlayer() {
		return player;
	}

}
