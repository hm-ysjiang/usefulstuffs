package hmysjiang.usefulstuffs.init;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	
	public static Block lightbulb;
	public static Block well;
	public static Block rain_detector;
	public static Block campfire;
	
	public static void init() {
		lightbulb = new BlockLightBulb();
		well = new BlockWell();
		rain_detector = new BlockRainDetector();
		campfire = new BlockCampfire();
	}
	
	public static void register() {
		registerBlock(lightbulb);
		registerBlock(well);
		registerBlock(rain_detector);
		registerBlock(campfire);
	}
	
	public static void registerBlock(Block block) {
		GameRegistry.register(block);
		ItemBlock item = new ItemBlock(block);
		item.setRegistryName(block.getRegistryName());
		GameRegistry.register(item);
	}
	
	public static void registerRenders() {
		registerRender(lightbulb);
		registerRender(well);
		registerRender(rain_detector);
		registerRender(campfire);
	}
	
	private static void registerRender(Block block) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, block.getUnlocalizedName().substring(18)), "inventory"));
	}
	
}