package hmysjiang.usefulstuffs.container;

import hmysjiang.usefulstuffs.items.baubles.ItemBackpack;
import invtweaks.api.container.ChestContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

@ChestContainer
public class ContainerBackpack extends ContainerItem {
	
	public ContainerBackpack(IInventory inv, ItemStack stack, int size, boolean onKey) {
		super(inv, stack, size);
		
		if (onKey)
			blocked = -1;
		
		int xH = 8, yH = 18;

		for (int y = 0 ; y < 6 ; y++) {
			for (int x = 0 ; x < 9 ; x++) {
				this.addSlotToContainer(new SlotNotBag(handler, x + (y * 9), xH + x * 18, yH + y * 18));
			}
		}

		
		int xPos = 8;
		int yPos = 140;
		
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(inv, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
			}
		}
		
		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(inv, x, xPos + x * 18, yPos + 58));
		}
	}
	
	public class SlotNotBag extends SlotItemHandler {

		public SlotNotBag(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
			super(itemHandler, index, xPosition, yPosition);
		}
		
		@Override
		public boolean isItemValid(ItemStack stackIn) {
			return super.isItemValid(stackIn) && !(stackIn.getItem() instanceof ItemBackpack);
		}
		
	}

}
