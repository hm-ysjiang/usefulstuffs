package hmysjiang.usefulstuffs.tileentity.capability;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class UnstackableItemStackHandler extends ItemStackHandler {
	
	public UnstackableItemStackHandler(int size) {
		super(size);
	}
	
	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (stack.isStackable())
			return stack;
//		for (int i = 0 ; i<getSlots() ; i++) {
//			if (stacks[i] == null) {
//				slot = i;
//			}
//		}
		return super.insertItem(slot, stack, simulate);
	}
	
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		ItemStack toReturn = super.extractItem(slot, amount, simulate);
//		if (toReturn != null) {
//			for (int i = slot+1 ; i<getSlots()-1 ; i++) {
//				if (stacks[i] == null)
//					return toReturn;
//				stacks[i-1] = stacks[i];
//				stacks[i] = null;
//			}
//		}
		return toReturn;
	}
	
}
