package hmysjiang.usefulstuffs.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.items.ItemMilkBag;
import hmysjiang.usefulstuffs.plugins.jei.MilkFermenterJei.MilkFermenterRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CheeseJei {
	public static final String UID = Reference.MOD_ID + ".jei.cheese";
	
	public static List<CheeseRecipe> getRecipes(IJeiHelpers jeiHelper){
		List<CheeseRecipe> recipes = new ArrayList<CheeseRecipe>();
		recipes.add(new CheeseRecipe(ItemMilkBag.fermented, new ItemStack(ModItems.cheese)));
		return recipes;
	}
	
	public static class CheeseCategory implements IRecipeCategory<CheeseRecipe>{

		protected final IDrawable background;
		protected final IDrawable icon;
		
		public CheeseCategory(IGuiHelper guiHelper) {
			background = guiHelper.createDrawable(new ResourceLocation(Reference.MOD_ID, "textures/gui/plugin/gui.png"), 101, 0, 80, 34);
			icon = guiHelper.createDrawableIngredient(new ItemStack(ModItems.cheese));
		}
		
		@Override
		public String getUid() {
			return UID;	
		}
		
		@Override
		public String getTitle() {
			return I18n.format("category.jei.usefulstuffs.cheese.title");
		}

		@Override
		public String getModName() {
			return Reference.NAME;
		}

		@Override
		public IDrawable getBackground() {
			return background;
		}
		
		@Override
		public IDrawable getIcon() {
			return icon;
		}

		@Override
		public void setRecipe(IRecipeLayout recipeLayout, CheeseRecipe recipeWrapper, IIngredients ingredients) {
			IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
			
			guiItemStacks.init(0, true, 8, 8);
			guiItemStacks.init(1, false, 54, 8);
			
			guiItemStacks.set(ingredients);
		}
		
	}
	
	public static class CheeseRecipe implements IRecipeWrapper{
		private final ItemStack input;
		private final ItemStack output;
		
		public CheeseRecipe(ItemStack input, ItemStack output) {
			this.input = input;
			this.output = output;
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInput(VanillaTypes.ITEM, input);
			ingredients.setOutput(VanillaTypes.ITEM, output);
		}
		
	}

}
