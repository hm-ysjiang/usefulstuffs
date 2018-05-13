<<<<<<< HEAD
package hmysjiang.usefulstuffs.miscs;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryCheck {
	
	/***
	 * Return the ItemStack object looking for in player's main inventory, null if not found
	 * @param playerIn
	 * @param itemIn
	 * @return
	 */
	public static ItemStack findItemStackInPlayerMainInventory(EntityPlayer playerIn, Item itemIn) {
		ItemStack item2find = new ItemStack(itemIn);
		for (ItemStack itemstack:playerIn.inventory.mainInventory)
			if (itemstack != null && itemstack.isItemEqual(item2find))
				return itemstack;
		return null;
	}
	
	public static ItemStack findItemStackInPlayerMainInventory(EntityPlayer playerIn, Block blockIn) {
		return findItemStackInPlayerMainInventory(playerIn, Item.getItemFromBlock(blockIn));
	}
	
}
=======
package hmysjiang.usefulstuffs.miscs;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryCheck {
	
	/***
	 * Return the ItemStack object looking for in player's main inventory, null if not found
	 * @param playerIn
	 * @param itemIn
	 * @return
	 */
	public static ItemStack findItemStackInPlayerMainInventory(EntityPlayer playerIn, Item itemIn) {
		ItemStack item2find = new ItemStack(itemIn);
		for (ItemStack itemstack:playerIn.inventory.mainInventory)
			if (itemstack != null && itemstack.isItemEqual(item2find))
				return itemstack;
		return null;
	}
	
	public static ItemStack findItemStackInPlayerMainInventory(EntityPlayer playerIn, Block blockIn) {
		return findItemStackInPlayerMainInventory(playerIn, Item.getItemFromBlock(blockIn));
	}
	
}
>>>>>>> Git init
