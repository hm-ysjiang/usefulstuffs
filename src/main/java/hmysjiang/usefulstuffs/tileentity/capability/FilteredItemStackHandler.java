package hmysjiang.usefulstuffs.tileentity.capability;

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
		for (Item item:filter) 
			if (stack.isItemEqual(new ItemStack(item)))
				return super.insertItem(slot, stack, simulate);
		return stack;
	}
	
}
