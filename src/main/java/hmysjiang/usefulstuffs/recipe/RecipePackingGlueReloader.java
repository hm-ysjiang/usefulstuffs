package hmysjiang.usefulstuffs.recipe;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.utils.helper.OreDictHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipePackingGlueReloader extends ShapelessOreRecipe {

	public RecipePackingGlueReloader() {
		super(new ResourceLocation(Reference.MOD_ID, "recipe"), new ItemStack(ModItems.packing_glue, 1, 16), "slimeball", new ItemStack(ModItems.packing_glue, 1, 32));
		setRegistryName(Reference.MOD_ID, "recipe_packing_glue_reload");
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		List<ItemStack> slimes = OreDictHelper.slimes;
		int glue = 0, slime = 0;
		for (int i = 0 ; i<inv.getSizeInventory() ; i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty()) {
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
		ItemStack glueStack = ItemStack.EMPTY;
		for (int i = 0 ; i<inv.getSizeInventory() ; i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty()) {
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
					return ItemStack.EMPTY;
				}
			}
		}
		if (!(glue == 1 && slime == 1))
			return ItemStack.EMPTY;
		ItemStack outStack = new ItemStack(ModItems.packing_glue, 1, glueStack.getItemDamage() > 16 ? glueStack.getItemDamage() - 16 : 0);
		if (glueStack.hasTagCompound())
			outStack.setTagCompound(glueStack.getTagCompound());
		return outStack;
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
