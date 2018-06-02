package hmysjiang.usefulstuffs.recipe;

import java.util.ArrayList;
import java.util.List;

import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeLightShooterCollector implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (int i = 0 ; i<inv.getSizeInventory() ; i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null) {
				if (!stack.isItemEqual(new ItemStack(ModItems.lightshooter)) && !stack.isItemEqual(new ItemStack(Blocks.HOPPER))) {
					return false;
				}
				list.add(stack);
			}
		}
		boolean shooter = false, hopper = false;
		for (ItemStack stack: list) {
			if (stack.isItemEqual(new ItemStack(ModItems.lightshooter)))
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
			if (stack != null) {
				if (!stack.isItemEqual(new ItemStack(ModItems.lightshooter)) && !stack.isItemEqual(new ItemStack(Blocks.HOPPER))) {
					return null;
				}
				list.add(stack);
			}
		}
		ItemStack stackShooter = null;
		boolean shooter = false, hopper = false;
		for (ItemStack stack: list) {
			if (stack.isItemEqual(new ItemStack(ModItems.lightshooter))) {
				stackShooter = stack;
				shooter = true;
			}
			else { 
				hopper = true;
			}
		}
		if (!shooter || !hopper || list.size() != 2)
			return null;
		ItemStack result = new ItemStack(ModItems.lightshooter_c, 1);
		result.setTagCompound(stackShooter.getTagCompound());
		return result;
	}

	@Override
	public int getRecipeSize() {
		return 4;
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];

        for (int i = 0; i < aitemstack.length; ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            aitemstack[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack);
        }

        return aitemstack;
	}

}
