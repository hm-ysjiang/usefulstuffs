package hmysjiang.usefulstuffs.recipe;

import java.util.ArrayList;
import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RecipeLightShooterCollector extends ShapelessRecipes {

	public RecipeLightShooterCollector() {
		super(Reference.MOD_ID + ":recipe", new ItemStack(ModItems.light_shooter_collecter), 
				NonNullList.<Ingredient>from(Ingredient.EMPTY, Ingredient.fromItem(Item.getItemFromBlock(Blocks.HOPPER)), Ingredient.fromItem(ModItems.light_shooter)));
		setRegistryName(Reference.MOD_ID, "recipe_lightshootercollector");
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (int i = 0 ; i<inv.getSizeInventory() ; i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty()) {
				if (!stack.isItemEqual(new ItemStack(ModItems.light_shooter)) && !stack.isItemEqual(new ItemStack(Blocks.HOPPER))) {
					return false;
				}
				list.add(stack);
			}
		}
		boolean shooter = false, hopper = false;
		for (ItemStack stack: list) {
			if (stack.isItemEqual(new ItemStack(ModItems.light_shooter)))
				shooter = true;
			else 
				hopper = true;
		}
		return shooter && hopper && list.size() == 2;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (int i = 0 ; i<inv.getSizeInventory() ; i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty()) {
				if (!stack.isItemEqual(new ItemStack(ModItems.light_shooter)) && !stack.isItemEqual(new ItemStack(Blocks.HOPPER))) {
					return ItemStack.EMPTY;
				}
				list.add(stack);
			}
		}
		ItemStack stackShooter = ItemStack.EMPTY;
		boolean shooter = false, hopper = false;
		for (ItemStack stack: list) {
			if (stack.isItemEqual(new ItemStack(ModItems.light_shooter))) {
				stackShooter = stack;
				shooter = true;
			}
			else { 
				hopper = true;
			}
		}
		if (!shooter || !hopper || list.size() != 2)
			return ItemStack.EMPTY;
		ItemStack result = new ItemStack(ModItems.light_shooter_collecter, 1);
		result.setTagCompound(stackShooter.getTagCompound());
		return result;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		NonNullList<ItemStack> aitemstack =NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);

		for (int i = 0; i < aitemstack.size(); ++i)
		{
			ItemStack itemstack = inv.getStackInSlot(i);
			aitemstack.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
		}

		return aitemstack;
	}

}
