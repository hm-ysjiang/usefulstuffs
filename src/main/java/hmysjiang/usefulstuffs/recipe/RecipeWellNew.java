package hmysjiang.usefulstuffs.recipe;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class RecipeWellNew extends ShapedRecipes {
	
	private static NonNullList<Ingredient> ingredients;
	public RecipeWellNew() {
		super(Reference.MOD_ID + ":recipe", 3, 3, ingredients, new ItemStack(ModBlocks.well));
		setRegistryName(new ResourceLocation(Reference.MOD_ID, "recipe_well_new"));
	}
	
	static {
		ingredients = NonNullList.<Ingredient>create();
		ingredients.add(Ingredient.EMPTY);
		ingredients.add(Ingredient.fromStacks(new ItemStack(ModItems.infinite_water, 1, 0)));
		ingredients.add(Ingredient.EMPTY);
		
		ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.HARDENED_CLAY)));
		ingredients.add(Ingredient.fromStacks(new ItemStack(Items.COMPARATOR)));
		ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.HARDENED_CLAY)));
		
		ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.HARDENED_CLAY)));
		ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.REDSTONE_TORCH)));
		ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.HARDENED_CLAY)));
	}

}
