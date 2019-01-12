package hmysjiang.usefulstuffs.init;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.recipe.RecipeLightShooterCollector;
import hmysjiang.usefulstuffs.recipe.RecipeMiningBackpack;
import hmysjiang.usefulstuffs.recipe.RecipePackingGlueReloader;
import hmysjiang.usefulstuffs.recipe.RecipeWell;
import hmysjiang.usefulstuffs.recipe.RecipeWellNew;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ModRecipe {
	
	public static NonNullList<Ingredient> recipeLightShooterCollector;
	
	public static void init() {
		recipeLightShooterCollector = NonNullList.<Ingredient>create();
		if (ConfigManager.lightShootersEnabled) {
			recipeLightShooterCollector.add(Ingredient.fromStacks(new ItemStack(Blocks.HOPPER, 1)));
			recipeLightShooterCollector.add(Ingredient.fromStacks(new ItemStack(ModItems.light_shooter, 1)));
		}
	}
		
	private static void registerMiscs() {
		ModPotion.registerBrewingRecipe();
	}
	
	@SubscribeEvent
	public static void onRecipeRegister(RegistryEvent.Register<IRecipe> event){
		registerMiscs();
		
		if (ConfigManager.lightShootersEnabled)
			event.getRegistry().register(new RecipeLightShooterCollector(recipeLightShooterCollector));
		if (ConfigManager.glueEnabled)
			event.getRegistry().register(new RecipePackingGlueReloader());
		if (ConfigManager.miningBackpackEnabled)
			event.getRegistry().register(new RecipeMiningBackpack());
		if (ConfigManager.wellEnabled) {
			if (ConfigManager.infiniteWaterEnabled)
				event.getRegistry().register(new RecipeWellNew());
			else 
				event.getRegistry().register(new RecipeWell());
		}
	}
	
}
