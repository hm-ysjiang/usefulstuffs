package hmysjiang.usefulstuffs.init;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.items.*;
import hmysjiang.usefulstuffs.items.crafting.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
	
	public static Item helper;
	public static Item lightshooter;
	public static Item lightshooter_c;
	public static Item waterfilter;
	public static Item umbrella;
	public static Item csu;
	public static Item bento;
	public static Item building_wand;
	public static Item building_wand_infinite;
	public static Item building_wizard;
	
	public static void init() {
		if (Reference.TEST_MODE)
			helper = new ItemMagicalWand();
		lightshooter = new ItemLightShooter();
		lightshooter_c = new ItemLightShooterCollector();
		waterfilter = new ItemWaterBlackListFilter();
		umbrella = new ItemUmbrella();
		csu = new ItemCompactStorageUnit();
		bento = new ItemBento();
		building_wand = new ItemBuildingWand(Reference.ModItems.BUILDING_WAND.getUnlocalizedName(), Reference.ModItems.BUILDING_WAND.getRegistryName());
		building_wand_infinite = new ItemBuildingWandInfinite(Reference.ModItems.BUILDING_WAND_INFINITE.getUnlocalizedName(), Reference.ModItems.BUILDING_WAND_INFINITE.getRegistryName());
		building_wizard = new ItemBuildingWizard();
		
	}
	
	public static void register() {
		if (Reference.TEST_MODE)
			GameRegistry.register(helper);
		GameRegistry.register(lightshooter);
		GameRegistry.register(lightshooter_c);
		GameRegistry.register(waterfilter);
		GameRegistry.register(umbrella);
		GameRegistry.register(csu);
		GameRegistry.register(bento);
		GameRegistry.register(building_wand);
		GameRegistry.register(building_wand_infinite);
		GameRegistry.register(building_wizard);
		
	}
	
	public static void registerRenders() {
		if (Reference.TEST_MODE)
			registerRender(helper);
		registerRender(lightshooter);
		registerRender(lightshooter_c);
		registerRender(waterfilter);
		registerRender(umbrella);
		registerRender(csu);
		registerRender(bento);
		registerRender(building_wand);
		registerRender(building_wand_infinite);
		registerRender(building_wizard);
		
	}
	
	private static void registerRender(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, item.getUnlocalizedName().substring(18)), "inventory"));
//		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	private static void registerRender(Item item, int meta, String filename) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, filename), "inventory"));
//		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
}