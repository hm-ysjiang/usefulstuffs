package hmysjiang.usefulstuffs.init;

import java.util.ArrayList;
import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.items.ItemBento;
import hmysjiang.usefulstuffs.items.ItemBuildingWand;
import hmysjiang.usefulstuffs.items.ItemBuildingWandInfinite;
import hmysjiang.usefulstuffs.items.ItemLightShooter;
import hmysjiang.usefulstuffs.items.ItemLightShooterCollector;
import hmysjiang.usefulstuffs.items.ItemPackingGlue;
import hmysjiang.usefulstuffs.items.ItemWaterBlackList;
import hmysjiang.usefulstuffs.items.baubles.ItemLilyBelt;
import hmysjiang.usefulstuffs.items.baubles.ItemStorageBag;
import hmysjiang.usefulstuffs.items.crafting.ItemBuildingWizard;
import hmysjiang.usefulstuffs.items.crafting.ItemCompactStorageUnit;
import hmysjiang.usefulstuffs.items.crafting.ItemFlipFlopCore;
import hmysjiang.usefulstuffs.items.crafting.ItemUmbrella;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class ModItems {
	
	public static List<Item> itemblocks = new ArrayList<>();
	
	public static Item water_blacklist;
	public static Item bento;
	public static Item building_wand;
	public static Item building_wand_infinite;
	public static Item light_shooter;
	public static Item light_shooter_collecter;
	public static Item packing_glue;
	public static Item building_wizard;
	public static Item compact_storage_unit;
	public static Item umbrella;
	public static Item belt_lily;
	public static Item bag_storage;
	public static Item flipflop_core;
	
	public static void init() {
		water_blacklist = new ItemWaterBlackList();
		bento = new ItemBento();
		building_wand = new ItemBuildingWand(Reference.ModItems.BUILDING_WAND.getUnlocalizedName(), Reference.ModItems.BUILDING_WAND.getRegistryName());
		building_wand_infinite = new ItemBuildingWandInfinite(Reference.ModItems.BUILDING_WAND_INFINITE.getUnlocalizedName(), Reference.ModItems.BUILDING_WAND_INFINITE.getRegistryName());
		light_shooter = new ItemLightShooter();
		light_shooter_collecter = new ItemLightShooterCollector();
		packing_glue = new ItemPackingGlue();
		building_wizard = new ItemBuildingWizard();
		compact_storage_unit = new ItemCompactStorageUnit();
		umbrella = new ItemUmbrella();
		belt_lily = new ItemLilyBelt();
		bag_storage = new ItemStorageBag();
		flipflop_core = new ItemFlipFlopCore();
	}
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		register(event.getRegistry(), water_blacklist,
				bento,
				building_wand,
				building_wand_infinite,
				light_shooter,
				light_shooter_collecter,
				packing_glue,
				building_wizard,
				compact_storage_unit,
				umbrella,
				belt_lily,
				bag_storage,
				flipflop_core);
		
		event.getRegistry().registerAll(itemblocks.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onModelRegistry(ModelRegistryEvent event) {
		UsefulStuffs.proxy.registerItemRenders(water_blacklist);
		UsefulStuffs.proxy.registerItemRenders(bento);
		UsefulStuffs.proxy.registerItemRenders(building_wand);
		UsefulStuffs.proxy.registerItemRenders(building_wand_infinite);
		UsefulStuffs.proxy.registerItemRenders(light_shooter);
		UsefulStuffs.proxy.registerItemRenders(light_shooter_collecter);
		UsefulStuffs.proxy.registerItemRenders(packing_glue);
		UsefulStuffs.proxy.registerItemRenders(building_wizard);
		UsefulStuffs.proxy.registerItemRenders(compact_storage_unit);
		UsefulStuffs.proxy.registerItemRenders(umbrella);
		UsefulStuffs.proxy.registerItemRenders(belt_lily);
		UsefulStuffs.proxy.registerItemRenders(bag_storage);
		UsefulStuffs.proxy.registerItemRenders(flipflop_core);
		
		for (Item item: itemblocks)
			UsefulStuffs.proxy.registerItemRenders(item);
	}
	
	private static void register(IForgeRegistry<Item> registry, Item... items) {
		registry.registerAll(items);
		for (Item item: items)
			item.setCreativeTab(UsefulStuffs.TAB);
	}
	
}
