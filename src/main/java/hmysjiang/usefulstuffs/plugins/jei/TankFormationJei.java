package hmysjiang.usefulstuffs.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;

public class TankFormationJei {
	public static final String UID = Reference.MOD_ID + ".tank_formation";
	
	public static List<TankFormationRecipe> getRecipes(IJeiHelpers jeiHelper){
		List<TankFormationRecipe> recipes = new ArrayList<TankFormationRecipe>();
		recipes.add(new TankFormationRecipe(new ItemStack(ModBlocks.tank), new ItemStack(ModItems.tank_container, 1, 0)
				 														 , new ItemStack(ModItems.tank_container, 1, 1)
																		 , new ItemStack(ModItems.tank_container, 1, 2)
																		 , new ItemStack(ModItems.tank_container, 1, 3)
																		 , new ItemStack(ModItems.tank_container, 1, 4)));
		return recipes;
	}
	
	public static class TankFormationCategory implements IRecipeCategory<TankFormationRecipe>{

		protected final IDrawable background;
		protected final IDrawable icon;
		
		public TankFormationCategory(IGuiHelper guiHelper) {
			background = guiHelper.createDrawable(new ResourceLocation(Reference.MOD_ID, "textures/gui/plugin/gui.png"), 0, 51, 100, 50);
			icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.tank));
		}
		
		@Override
		public String getUid() {
			return UID;	
		}
		
		@Override
		public String getTitle() {
			return I18n.format("category.jei.usefulstuffs.tank_formation.title");
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
		public void setRecipe(IRecipeLayout recipeLayout, TankFormationRecipe recipeWrapper, IIngredients ingredients) {
			IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
			
			guiItemStacks.init(0, true, 28, 5);
			guiItemStacks.init(1, false, 54, 27);
			
			guiItemStacks.set(ingredients);
		}
	}
	
	public static class TankFormationRecipe implements IRecipeWrapper{
		private final ItemStack[] items;
		private final ItemStack block;
		
		public TankFormationRecipe(ItemStack block, ItemStack... items) {
			this.items = items;
			this.block = block;
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputLists(VanillaTypes.ITEM, new ArrayList<>(Arrays.asList(new List[] {Arrays.asList(this.items)})));
			ingredients.setOutput(VanillaTypes.ITEM, block);
		}
		
	}

}
