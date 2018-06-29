package hmysjiang.usefulstuffs.container;

import java.util.List;
import java.util.ArrayList;

import hmysjiang.usefulstuffs.container.slot.SlotFilingCabinet;
import hmysjiang.usefulstuffs.tileentity.TileEntityFilingCabinet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerFilingCabinet extends Container {
	
	protected EntityPlayer player;
	protected InventoryPlayer playerInv;
	protected BlockPos pos;
	protected TileEntityFilingCabinet tile;
	protected int page;
	
	public ContainerFilingCabinet(EntityPlayer player, BlockPos pos, int page) {
		this.player = player;
		this.playerInv = player.inventory;
		this.pos = pos;
		this.tile = (TileEntityFilingCabinet) player.worldObj.getTileEntity(pos);
		this.page = page;
		
		IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		int xFC = 7, yFC = 18, gap = 18;
		//18*6*10
		for (int y = 0 ; y < 6 ; y++) {
			for (int x = 0 ; x < 18 ; x++) {
				this.addSlotToContainer(new SlotFilingCabinet(handler, x + (y * 18) + (page * 108), xFC + x *gap, yFC + y *gap));
			}
		}
		
		int xPos = 79, yPos = 140;
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
			}
		}
		
		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(playerInv, x, xPos + x * 18, yPos + 58));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return !playerIn.isSpectator();
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 108) {
            	if (!this.mergeItemStack(itemstack1, 108, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 108, false)) {
            	return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
	}
	
	public void sort() {
		List<ItemStack> newlist = new ArrayList<ItemStack>();
		for (ItemStack stack: inventoryItemStacks) {
			if (stack != null) {
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
	
	public TileEntityFilingCabinet getTile() {
		return tile;
	}
	
	public BlockPos getPos() {
		return pos;
	}

}
