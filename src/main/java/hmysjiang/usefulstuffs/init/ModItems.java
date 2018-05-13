package hmysjiang.usefulstuffs.init;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.handlers.EnumHandler.ExcaliburType;
import hmysjiang.usefulstuffs.items.ItemExcalibur;
import hmysjiang.usefulstuffs.items.ItemGaeBolg;
import hmysjiang.usefulstuffs.items.ItemLightShooter;
import hmysjiang.usefulstuffs.items.ItemLightShooterCollector;
import hmysjiang.usefulstuffs.items.ItemMagicalWand;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
	
	public static Item helper;
	public static Item excalibur;
	public static Item lightshooter;
	public static Item lightshooter_c;
	public static Item gaebolg;
	
	public static void init() {
		excalibur = new ItemExcalibur();
		helper = new ItemMagicalWand();
		lightshooter = new ItemLightShooter();
		lightshooter_c = new ItemLightShooterCollector();
		gaebolg = new ItemGaeBolg();
	}
	
	public static void register() {
		GameRegistry.register(helper);
		GameRegistry.register(excalibur);
		GameRegistry.register(lightshooter);
		GameRegistry.register(lightshooter_c);
		GameRegistry.register(gaebolg);
	}
	
	public static void registerRenders() {
		registerRender(helper);
		registerRender(lightshooter);
		registerRender(lightshooter_c);
		registerRender(gaebolg);
		for (int i = 0;i<ExcaliburType.values().length;i++) {
			registerRender(excalibur, i, "excalibur_" + ExcaliburType.values()[i].getName());
		}
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
 