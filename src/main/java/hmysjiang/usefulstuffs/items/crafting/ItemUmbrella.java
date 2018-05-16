package hmysjiang.usefulstuffs.items.crafting;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemUmbrella extends Item {
	
	public ItemUmbrella() {
		setUnlocalizedName(Reference.ModItems.UMBRELLA.getUnlocalizedName());
		setRegistryName(Reference.ModItems.UMBRELLA.getRegistryName());
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(I18n.format("usefulstuffs.ingredient.tooltip"));
		super.addInformation(stack, playerIn, tooltip, advanced);
	}
	
}
