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
	
//	public static Item helper;
	public static Item lightshooter;
	public static Item lightshooter_c;
	public static Item waterfilter;
	public static Item umbrella;
	public static Item csu;
	public static Item quiver;
	public static Item bow_and_quiver;
	
	public static void init() {
//		helper = new ItemMagicalWand();
		lightshooter = new ItemLightShooter();
		lightshooter_c = new ItemLightShooterCollector();
		waterfilter = new ItemWaterBlackListFilter();
		umbrella = new ItemUmbrella();
		csu = new ItemCompactStorageUnit();
		quiver = new ItemQuiver();
		bow_and_quiver = new ItemBowQuiver();
	}
	
	public static void register() {
//		GameRegistry.register(helper);
		GameRegistry.register(lightshooter);
		GameRegistry.register(lightshooter_c);
		GameRegistry.register(waterfilter);
		GameRegistry.register(umbrella);
		GameRegistry.register(csu);
		GameRegistry.register(quiver);
		GameRegistry.register(bow_and_quiver);
	}
	
	public static void registerRenders() {
//		registerRender(helper);
		registerRender(lightshooter);
		registerRender(lightshooter_c);
		registerRender(waterfilter);
		registerRender(umbrella);
		registerRender(csu);
		registerRender(quiver);
		registerRender(bow_and_quiver);
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