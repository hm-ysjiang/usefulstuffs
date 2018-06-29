package hmysjiang.usefulstuffs.tileentity.capability;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackHandlerWell extends ItemStackHandler {
	
	private List<Item> filter;
	
	public ItemStackHandlerWell(int size) {
		super(size);
		this.filter = new ArrayList<Item>();
	}
	
	public ItemStackHandlerWell() {
		this(1);
	}
	
	public List<Item> getFilteredItems() {
		return this.filter;
	}
	
	public ItemStackHandlerWell setFilteredItem(Item... items) {
		for (Item item:items) {
			filter.add(item);
		}
		return this;
	}
	
	public void addItemToFilter(Item item) {
		filter.add(item);
	}
	
	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		for (Item item:filter) 
			if (stack.isItemEqualIgnoreDurability(new ItemStack(item)))
				return super.insertItem(slot, stack, simulate);
		return stack;
	}
	
}
