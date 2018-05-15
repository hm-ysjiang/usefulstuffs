package hmysjiang.usefulstuffs.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class FilteredItemStackHandler extends ItemStackHandler {
	
	private List<Item> filter;
	
	public FilteredItemStackHandler(int size) {
		super(size);
		this.filter = new ArrayList<Item>();
	}
	
	public FilteredItemStackHandler() {
		this(1);
	}
	
	public List<Item> getFilteredItems() {
		return this.filter;
	}
	
	public FilteredItemStackHandler setFilteredItem(Item item) {
		this.filter.add(item);
		return this;
	}
	
	public void addItemToFilter(Item item) {
		filter.add(item);
	}
	
	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (stack == null || stack.stackSize == 0)
			return null;
		if (slot != 0 || this.stacks[0] != null)
			return stack;
		if (!simulate) {
			this.stacks[0] = stack;
			return null;
		}
		return super.insertItem(slot, stack, simulate);
	}
	
}
