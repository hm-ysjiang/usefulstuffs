package hmysjiang.usefulstuffs.plugins.jei;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.client.gui.GuiMilkFermenter;
import hmysjiang.usefulstuffs.init.ModBlocks;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class UsefulStuffsJeiPlugin implements IModPlugin{
	
	@Override
	public void register(IModRegistry registry) {
		IJeiHelpers jeiHelper = registry.getJeiHelpers();
		IModRegistry jeiRegistry = registry;
		//Item Description
		ItemJeiDescription.registerDescription(registry);
		if (ConfigManager.milkFermenterEnabled) {
			//Custom Recipes
			registry.addRecipes(MilkFermenterJei.getRecipes(jeiHelper), MilkFermenterJei.UID);
			//Recipe Catalyst
			registry.addRecipeCatalyst(new ItemStack(ModBlocks.milk_fermenter), MilkFermenterJei.UID);
			//Click Recipe
			registry.addRecipeClickArea(GuiMilkFermenter.class, 80, 48, 16, 16, MilkFermenterJei.UID);
		}
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
		if (ConfigManager.milkFermenterEnabled)
			registry.addRecipeCategories(new MilkFermenterJei.MilkFermenterCategory(guiHelper));
	}
	
}
