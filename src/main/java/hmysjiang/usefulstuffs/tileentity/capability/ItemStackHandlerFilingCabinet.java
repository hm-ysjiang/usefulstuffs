package hmysjiang.usefulstuffs.tileentity.capability;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackHandlerFilingCabinet extends ItemStackHandler {
	
	public ItemStackHandlerFilingCabinet(int size) {
		super(size);
	}
	
	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (stack.isStackable())
			return stack;
		return super.insertItem(slot, stack, simulate);
	}
	
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		ItemStack toReturn = super.extractItem(slot, amount, simulate);
		return toReturn;
	}
	
	public void sort() {
		ItemStack[] stacksNew = new ItemStack[stacks.length];
		int p = 0;
		for (ItemStack stack: stacks) {
			if (stack != null) {
				stacksNew[p++] = stack;
			}
		}
		this.stacks = stacksNew.clone();
	}
	
}
