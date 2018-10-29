package hmysjiang.usefulstuffs.utils.helper;

import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class InventoryHelper {

	public static ItemStack findStackInPlayerInventory(EntityPlayer player, ItemStack template, boolean ignoreDurability) {
		InventoryPlayer playerInv = player.inventory;
		for (ItemStack stack:playerInv.mainInventory) {
			if (stack.isEmpty())
				continue;
			if (ignoreDurability ? stack.isItemEqualIgnoreDurability(template) : stack.isItemEqual(template)) {
				return stack;
			}
		}
		for (ItemStack stack:playerInv.offHandInventory) {
			if (stack.isEmpty())
				continue;
			if (ignoreDurability ? stack.isItemEqualIgnoreDurability(template) : stack.isItemEqual(template)) {
				return stack;
			}
		}
		return ItemStack.EMPTY;
	}
	
	public static ItemStack findStackInPlayerInventoryContainBaubles(EntityPlayer player, ItemStack template, boolean ignoreDurability) {
		IBaublesItemHandler handler = player.getCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null);
		for (int i = 0 ; i<handler.getSlots() ; i++) 
			if (ignoreDurability ? handler.getStackInSlot(i).isItemEqualIgnoreDurability(template) : handler.getStackInSlot(i).isItemEqual(template))
				return handler.getStackInSlot(i);
		return findStackInPlayerInventory(player, template, ignoreDurability);
	}
	
}
