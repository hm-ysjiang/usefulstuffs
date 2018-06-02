package hmysjiang.usefulstuffs.recipe;

import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModCraftingRecipes {
	
	public static void register() {
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.lightbulb, 8), " P ", "PGP", " P ", 'P', Blocks.GLASS_PANE, 'G', Items.GLOWSTONE_DUST);
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.lightshooter, 1), " CP", "OOR", " BO", 'C', Blocks.CHEST, 'P', Blocks.PISTON, 'O', Blocks.OBSIDIAN, 'R', Blocks.REDSTONE_TORCH, 'B', Blocks.STONE_BUTTON);
		GameRegistry.addRecipe(new RecipeLightShooterCollector());
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.well, 1), "WPW", "HCH", "HRH", 'W', Items.WATER_BUCKET, 'P', Blocks.PISTON, 'H', Blocks.HARDENED_CLAY, 'C', Items.COMPARATOR, 'R', Blocks.REDSTONE_TORCH);
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.umbrella, 1), "WBW", " I ", " I ", 'W', new ItemStack(Blocks.WOOL, 1, 0), 'B', new ItemStack(Blocks.WOOL, 1, 11), 'I', Items.IRON_INGOT);
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.rain_detector, 1), " U ", " B ", "RSR", 'U', ModItems.umbrella, 'B', Blocks.WOODEN_BUTTON, 'R', Items.REDSTONE, 'S', Blocks.STONE);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.waterfilter), new ItemStack(Items.PAPER, 1), new ItemStack(ModItems.umbrella, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.waterfilter), new ItemStack(ModItems.waterfilter));
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.csu, 1), "CCC", "CDC", "CCC", 'C', Blocks.CHEST, 'D', Items.DIAMOND);
		GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.filing_cabinet, 1), "WUP", "UUU", "SUA", 'W', new ItemStack(Items.GOLDEN_SWORD, 1), 'P', new ItemStack(Items.GOLDEN_PICKAXE, 1), 'S', new ItemStack(Items.GOLDEN_SHOVEL, 1), 'A', new ItemStack(Items.GOLDEN_AXE, 1), 'U', ModItems.csu);
	}
	
}
