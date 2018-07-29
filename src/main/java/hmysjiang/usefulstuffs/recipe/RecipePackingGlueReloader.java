package hmysjiang.usefulstuffs.recipe;

import java.util.List;

import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.utils.helper.LogHelper;
import hmysjiang.usefulstuffs.utils.helper.OreDictHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipePackingGlueReloader extends ShapelessOreRecipe {

	public RecipePackingGlueReloader() {
		super(new ItemStack(ModItems.packing_glue, 1, 16), "slimeball", new ItemStack(ModItems.packing_glue, 1, 32));
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		List<ItemStack> slimes = OreDictHelper.slimes;
		int glue = 0, slime = 0;
		for (int i = 0 ; i<inv.getSizeInventory() ; i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null) {
				boolean f = false;
				for (ItemStack slimeOre:slimes) {
					if (stack.isItemEqual(slimeOre)) {
						slime++;
						f = true;
						break;
					}
				}
				if (f)
					continue;
				if (stack.isItemEqualIgnoreDurability(new ItemStack(ModItems.packing_glue))) {
					glue++;
				}
				else {
					return false;
				}
			}
		}
		return glue == 1 && slime == 1;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		List<ItemStack> slimes = OreDictHelper.slimes;
		int glue = 0, slime = 0;
		ItemStack glueStack = null;
		for (int i = 0 ; i<inv.getSizeInventory() ; i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null) {
				boolean f = false;
				for (ItemStack slimeOre:slimes) {
					if (stack.isItemEqual(slimeOre)) {
						slime++;
						f = true;
						break;
					}
				}
				if (f)
					continue;
				if (stack.isItemEqualIgnoreDurability(new ItemStack(ModItems.packing_glue))) {
					glueStack = stack;
					glue++;
				}
				else {
					return null;
				}
			}
		}
		if (!(glue == 1 && slime == 1))
			return null;
		ItemStack outStack = new ItemStack(ModItems.packing_glue, 1, glueStack.getItemDamage() > 16 ? glueStack.getItemDamage() - 16 : 0);
		if (glueStack.hasTagCompound())
			outStack.setTagCompound(glueStack.getTagCompound());
		return outStack;
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
