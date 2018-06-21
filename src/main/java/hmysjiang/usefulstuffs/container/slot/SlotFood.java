package hmysjiang.usefulstuffs.container.slot;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotFood extends SlotItemHandler {

	public SlotFood(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return super.isItemValid(stack) && stack.getItem() instanceof ItemFood;
	}

}
