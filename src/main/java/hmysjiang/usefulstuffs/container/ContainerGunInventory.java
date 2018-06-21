package hmysjiang.usefulstuffs.container;

import hmysjiang.usefulstuffs.container.slot.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerGunInventory extends ContainerItem {
	
	public ContainerGunInventory(IInventory inv, ItemStack stack, int size) {
		super(inv, stack, size);
		
		this.addSlotToContainer(new SlotBullet(handler, 0, 57, 35));
		this.addSlotToContainer(new SlotShell(handler, 1, 103, 17));
		this.addSlotToContainer(new SlotShell(handler, 2, 103, 35));
		this.addSlotToContainer(new SlotShell(handler, 3, 103, 53));
		
		int xPos = 8;
		int yPos = 84;
		
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
