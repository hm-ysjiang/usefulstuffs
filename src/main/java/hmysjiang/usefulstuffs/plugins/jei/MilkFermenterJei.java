package hmysjiang.usefulstuffs.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.items.ItemMilkBag;
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

public class MilkFermenterJei {
	public static final String UID = Reference.MOD_ID + ".milk_fermenter";
	
	public static List<MilkFermenterRecipe> getRecipes(IJeiHelpers jeiHelper){
		List<MilkFermenterRecipe> recipes = new ArrayList<MilkFermenterRecipe>();
		recipes.add(new MilkFermenterRecipe(new ItemStack(ModItems.milk_bag), ItemMilkBag.fermented));
		return recipes;
	}
	
	public static class MilkFermenterCategory implements IRecipeCategory<MilkFermenterRecipe>{

		protected final IDrawableAnimated cheese;
		protected final IDrawable background;
		protected final IDrawable icon;
		
		public MilkFermenterCategory(IGuiHelper guiHelper) {
			cheese = guiHelper.drawableBuilder(new ResourceLocation(Reference.MOD_ID, "textures/gui/container/fermenter.png"), 176, 0, 16, 16)
					 		  .buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
			background = guiHelper.createDrawable(new ResourceLocation(Reference.MOD_ID, "textures/gui/plugin/gui.png"), 0, 0, 100, 50);
			icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.milk_fermenter));
		}
		
		@Override
		public String getUid() {
			return UID;	
		}
		
		@Override
		public String getTitle() {
			return I18n.format("category.jei.usefulstuffs.milk_fermenter.title");
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
		
		public void drawExtras(net.minecraft.client.Minecraft minecraft) {
			cheese.draw(minecraft, 42, 17);
		}

		@Override
		public void setRecipe(IRecipeLayout recipeLayout, MilkFermenterRecipe recipeWrapper, IIngredients ingredients) {
			IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
			
			guiItemStacks.init(0, true, 15, 16);
			guiItemStacks.init(1, false, 67, 16);
			
			guiItemStacks.set(ingredients);
		}
		
	}
	
	public static class MilkFermenterRecipe implements IRecipeWrapper{
		private final ItemStack input;
		private final ItemStack output;
		
		public MilkFermenterRecipe(ItemStack input, ItemStack output) {
			this.input = input;
			this.output = output;
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInput(VanillaTypes.ITEM, input);
			ingredients.setOutput(VanillaTypes.ITEM, output);
		}
		
		@Override
		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
			minecraft.fontRenderer.drawString(I18n.format("usefulstuffs.fermenter.jei.gui", ConfigManager.milkFermentTime), 0, 37, 4210752);
		}
		
	}
	
}
