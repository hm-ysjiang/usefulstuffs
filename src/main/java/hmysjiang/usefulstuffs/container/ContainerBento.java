package hmysjiang.usefulstuffs.container;

import hmysjiang.usefulstuffs.container.slot.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBento extends ContainerItem {
	
	public ContainerBento(IInventory inv, ItemStack stack, int size) {
		super(inv, stack, size);
		
		this.addSlotToContainer(new SlotFood(handler, 0, 35, 20));
		this.addSlotToContainer(new SlotFood(handler, 1, 53, 20));
		this.addSlotToContainer(new SlotFood(handler, 2, 71, 20));
		this.addSlotToContainer(new SlotFood(handler, 3, 89, 20));
		this.addSlotToContainer(new SlotFood(handler, 4, 107, 20));
		this.addSlotToContainer(new SlotFood(handler, 5, 125, 20));
		
		int xPos = 8;
		int yPos = 51;
		
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(inv, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
			}
		}
		
		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(inv, x, xPos + x * 18, yPos + 58));
		}
	}

}
