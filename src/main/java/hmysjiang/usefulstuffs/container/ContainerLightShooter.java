package hmysjiang.usefulstuffs.container;

import hmysjiang.usefulstuffs.blocks.lightbulb.BlockLightBulb;
import hmysjiang.usefulstuffs.container.ContainerBento.SlotFood;
import invtweaks.api.container.ChestContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

@ChestContainer
public class ContainerLightShooter extends ContainerItem {

	public ContainerLightShooter(IInventory inv, ItemStack stack, int size) {
		super(inv, stack, size);
		
		this.addSlotToContainer(new SlotLightBulb(handler, 0, 53, 20));
		this.addSlotToContainer(new SlotLightBulb(handler, 1, 71, 20));
		this.addSlotToContainer(new SlotLightBulb(handler, 2, 89, 20));
		this.addSlotToContainer(new SlotLightBulb(handler, 3, 107, 20));
		
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
	
	public class SlotLightBulb extends SlotItemHandler {

		public SlotLightBulb(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
			super(itemHandler, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return super.isItemValid(stack) && stack.getItem() instanceof ItemBlock && ((ItemBlock) stack.getItem()).getBlock() instanceof BlockLightBulb;
		}
		
	}

}
