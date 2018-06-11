package hmysjiang.usefulstuffs.miscs.helper;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryHelper {
	
	public static ItemStack findItemStackInPlayerInventory(EntityPlayer playerIn, Item itemIn) {
		if (findItemStackInPlayerOffHand(playerIn, itemIn) != null)
			return findItemStackInPlayerOffHand(playerIn, itemIn);
		return findItemStackInPlayerMainInventory(playerIn, itemIn);
	}
	
	public static ItemStack findItemStackInPlayerInventory(EntityPlayer playerIn, Block blockIn) {
		return findItemStackInPlayerInventory(playerIn, Item.getItemFromBlock(blockIn));
	}
	
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
	
	public static ItemStack findItemStackInPlayerOffHand(EntityPlayer playerIn, Item itemIn) {
		ItemStack item2find = new ItemStack(itemIn);
		for (ItemStack itemstack:playerIn.inventory.offHandInventory)
			if (itemstack != null && itemstack.isItemEqual(item2find))
				return itemstack;
		return null;
	}
	
	public static ItemStack findItemStackInPlayerOffHand(EntityPlayer playerIn, Block blockIn) {
		return findItemStackInPlayerOffHand(playerIn, Item.getItemFromBlock(blockIn));
	}
	
}