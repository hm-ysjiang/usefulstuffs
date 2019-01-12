package hmysjiang.usefulstuffs.container;

import hmysjiang.usefulstuffs.blocks.filingcabinets.TileEntityFilingCabinetUnstackable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFilingCabinet extends ContainerBase {

	protected EntityPlayer player;
	protected BlockPos pos;
	protected TileEntityFilingCabinetUnstackable tile;
	protected int page;
	
	public ContainerFilingCabinet(EntityPlayer player, BlockPos pos, int page) {
		super(player.inventory, (ItemStackHandler) ((TileEntityFilingCabinetUnstackable) player.world.getTileEntity(pos)).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
		this.player = player;
		this.pos = pos;
		this.tile = (TileEntityFilingCabinetUnstackable) player.world.getTileEntity(pos);
		this.page = page;
		
		int xFC = 7, yFC = 18, gap = 18;
		//18*6*10
		for (int y = 0 ; y < 6 ; y++) {
			for (int x = 0 ; x < 18 ; x++) {
				this.addSlotToContainer(new SlotUnstackable(handler, x + (y * 18) + (page * 108), xFC + x *gap, yFC + y *gap));
			}
		}
		
		int xPos = 79, yPos = 140;
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(player.inventory, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
			}
		}
		
		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(player.inventory, x, xPos + x * 18, yPos + 58));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 108) {
            	if (!this.mergeItemStack(itemstack1, 108, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 108, false)) {
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
	
	public void sort() {
		NonNullList<ItemStack> newlist = NonNullList.<ItemStack>create();
		for (ItemStack stack: inventoryItemStacks) {
			if (!stack.isEmpty()) {
				newlist.add(stack);
			}
		}
		this.inventoryItemStacks = newlist;
	}
	
	public int getPage() {
		return page;
	}
	
	public EntityPlayer getPlayer() {
		return player;
	}
	
	public TileEntityFilingCabinetUnstackable getTile() {
		return tile;
	}
	
	public BlockPos getPos() {
		return pos;
	}
	
	public class SlotUnstackable extends SlotItemHandler {

		public SlotUnstackable(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
			super(itemHandler, index, xPosition, yPosition);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			return super.isItemValid(stack) && stack.getItem().getItemStackLimit(stack) == 1;
		}

	}
	
}
