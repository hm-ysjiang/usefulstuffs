package hmysjiang.usefulstuffs.items.crafting;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ItemCraftingIngredient extends Item {
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, worldIn, tooltip, advanced);
		tooltip.add(I18n.format("usefulstuffs.ingredient.tooltip"));
	}
	
}
