package hmysjiang.usefulstuffs.recipe;

import javax.annotation.Nonnull;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.items.ItemTankContainer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;

public class RecipeTankContainer extends ShapedRecipes {

	public RecipeTankContainer(int toTier) {
		super(Reference.MOD_ID + ":recipe", 3, 3, getIngredients(toTier), getResult(toTier));
		setRegistryName(Reference.MOD_ID, "recipe_tank_upgrade_" + ItemTankContainer.TankTier.byMeta(toTier).getName());
	}
	
	@Nonnull
	private static ItemStack getResult(int toTier) {
		if (ItemTankContainer.TankTier.byMeta(toTier).availiable() && toTier != 0)
			return new ItemStack(ModItems.tank_container, 1, toTier);
		return ItemStack.EMPTY;
	}

	private static NonNullList<Ingredient> getIngredients(int toTier){
		NonNullList<Ingredient> ingredients = NonNullList.<Ingredient>create();
		if (ItemTankContainer.TankTier.byMeta(toTier).availiable() && toTier != 0) {
			switch (toTier) {
			case 1:
				ingredients.add(Ingredient.fromStacks(new ItemStack(Items.GOLD_INGOT)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Items.GOLD_INGOT)));
				
				ingredients.add(Ingredient.fromStacks(new ItemStack(Items.GOLD_INGOT)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(ModItems.tank_container, 1, 0)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Items.GOLD_INGOT)));
				
				ingredients.add(Ingredient.fromStacks(new ItemStack(Items.GOLD_INGOT)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Items.GOLD_INGOT)));
				break;
			case 2:
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.GLASS)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.GLASS)));
				
				ingredients.add(Ingredient.fromStacks(new ItemStack(Items.DIAMOND)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(ModItems.tank_container, 1, 1)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Items.DIAMOND)));
				
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.GLASS)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.GLASS)));
				break;
			case 3:
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.OBSIDIAN)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Items.DIAMOND)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.OBSIDIAN)));
				
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.OBSIDIAN)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(ModItems.tank_container, 1, 2)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.OBSIDIAN)));
				
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.OBSIDIAN)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Items.DIAMOND)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.OBSIDIAN)));
				break;
			case 4:
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.GLASS)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Items.NETHER_STAR)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.GLASS)));
				
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.GLASS)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(ModItems.tank_container, 1, 3)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.GLASS)));
				
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.GLASS)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.OBSIDIAN)));
				ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.GLASS)));
				break;
			}
		}
		return ingredients;
	}
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack result = super.getCraftingResult(inv);
		ItemStack input = inv.getStackInSlot(4);
		if (input.hasTagCompound())
			result.setTagCompound(input.getTagCompound().copy());
		return result;
	}

}
