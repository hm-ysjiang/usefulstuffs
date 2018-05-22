package hmysjiang.usefulstuffs.miscs.handler;

import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeHandler {
	
	public static void register() {
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.waterfilter), new ItemStack(ModItems.waterfilter));
	}
	
}
