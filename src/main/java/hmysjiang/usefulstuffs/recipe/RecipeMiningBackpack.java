package hmysjiang.usefulstuffs.recipe;

import org.omg.CORBA.INV_FLAG;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.items.baubles.ItemMiningBackpack;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;

public class RecipeMiningBackpack extends ShapedRecipes {
	
	public static NonNullList<Ingredient> ingredients = NonNullList.<Ingredient>create();

	public RecipeMiningBackpack() {
		super(Reference.MOD_ID + ":recipe", 3, 3, ingredients, new ItemStack(ModItems.mining_backpack));
		setRegistryName(Reference.MOD_ID, "recipe_mining_backpack");
	}
	
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack result = super.getCraftingResult(inv);
		ItemStack stack = inv.getStackInSlot(4);
		if (stack.hasTagCompound())
			result.setTagCompound(stack.getTagCompound().copy());
		ItemMiningBackpack.setDefaultTag(result);
		return result;
	}
	
	static {
		ingredients.add(Ingredient.fromStacks(ItemStack.EMPTY));
		ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.HOPPER)));
		ingredients.add(Ingredient.fromStacks(ItemStack.EMPTY));
		ingredients.add(Ingredient.fromStacks(new ItemStack(Items.QUARTZ)));
		ingredients.add(Ingredient.fromStacks(new ItemStack(ModItems.backpack)));
		ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.IRON_ORE)));
		ingredients.add(Ingredient.fromStacks(ItemStack.EMPTY));
		ingredients.add(Ingredient.fromStacks(new ItemStack(Blocks.HOPPER)));
		ingredients.add(Ingredient.fromStacks(ItemStack.EMPTY));
	}

}
