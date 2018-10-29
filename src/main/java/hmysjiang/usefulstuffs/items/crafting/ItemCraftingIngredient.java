package hmysjiang.usefulstuffs.items.crafting;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCraftingIngredient extends Item {
	
	@Deprecated
	public ItemCraftingIngredient() {}
	
	public ItemCraftingIngredient(String registryName, String unlocalizedName) {
		setUnlocalizedName(unlocalizedName);
		setRegistryName(registryName);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, worldIn, tooltip, advanced);
		tooltip.add(I18n.format("usefulstuffs.ingredient.tooltip"));
	}
	
}
