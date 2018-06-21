package hmysjiang.usefulstuffs.init;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.items.ItemBento;
import hmysjiang.usefulstuffs.items.ItemLightShooter;
import hmysjiang.usefulstuffs.items.ItemLightShooterCollector;
import hmysjiang.usefulstuffs.items.ItemWaterBlackListFilter;
import hmysjiang.usefulstuffs.items.crafting.ItemBullethead;
import hmysjiang.usefulstuffs.items.crafting.ItemCompactStorageUnit;
import hmysjiang.usefulstuffs.items.crafting.ItemUmbrella;
import hmysjiang.usefulstuffs.items.gun.ItemBullet;
import hmysjiang.usefulstuffs.items.gun.ItemHandGun;
import hmysjiang.usefulstuffs.items.variants.EnumBulletHead;
import hmysjiang.usefulstuffs.items.variants.EnumBulletVariants;
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
	public static Item bullet;
	public static Item hand_gun;
	public static Item bento;
	public static Item bullet_head;
	
	public static void init() {
//		helper = new ItemMagicalWand();
		lightshooter = new ItemLightShooter();
		lightshooter_c = new ItemLightShooterCollector();
		waterfilter = new ItemWaterBlackListFilter();
		umbrella = new ItemUmbrella();
		csu = new ItemCompactStorageUnit();
		bullet = new ItemBullet();
		bullet_head = new ItemBullethead();
		hand_gun = new ItemHandGun();
		bento = new ItemBento();
	}
	
	public static void register() {
//		GameRegistry.register(helper);
		GameRegistry.register(lightshooter);
		GameRegistry.register(lightshooter_c);
		GameRegistry.register(waterfilter);
		GameRegistry.register(umbrella);
		GameRegistry.register(csu);
		GameRegistry.register(bullet);
		GameRegistry.register(bullet_head);
		GameRegistry.register(hand_gun);
		GameRegistry.register(bento);
	}
	
	public static void registerRenders() {
//		registerRender(helper);
		registerRender(lightshooter);
		registerRender(lightshooter_c);
		registerRender(waterfilter);
		registerRender(umbrella);
		registerRender(csu);
		
		for (int i = 0 ; i<EnumBulletVariants.values().length ; i++) {
			registerRender(bullet, i, "bullet_" + EnumBulletVariants.values()[i].getName());
		}
		for (int i = 0 ; i<EnumBulletHead.values().length ; i++) {
			registerRender(bullet, i, "bullet_head_" + EnumBulletVariants.values()[i].getName());
		}
		
		registerRender(hand_gun);
		registerRender(bento);
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