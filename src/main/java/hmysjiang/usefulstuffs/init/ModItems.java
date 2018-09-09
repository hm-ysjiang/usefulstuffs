package hmysjiang.usefulstuffs.init;

import java.util.ArrayList;
import java.util.List;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.blocks.bush.EnumBerryColor;
import hmysjiang.usefulstuffs.items.ItemBento;
import hmysjiang.usefulstuffs.items.ItemBerry;
import hmysjiang.usefulstuffs.items.ItemBuildingWand;
import hmysjiang.usefulstuffs.items.ItemBuildingWandInfinite;
import hmysjiang.usefulstuffs.items.ItemCheese;
import hmysjiang.usefulstuffs.items.ItemInfiniteWater;
import hmysjiang.usefulstuffs.items.ItemLightShooter;
import hmysjiang.usefulstuffs.items.ItemLightShooterCollector;
import hmysjiang.usefulstuffs.items.ItemMilkBag;
import hmysjiang.usefulstuffs.items.ItemPackingGlue;
import hmysjiang.usefulstuffs.items.ItemWaterBlackList;
import hmysjiang.usefulstuffs.items.baubles.ItemBackpack;
import hmysjiang.usefulstuffs.items.baubles.ItemFieryLilyBelt;
import hmysjiang.usefulstuffs.items.baubles.ItemLilyBelt;
import hmysjiang.usefulstuffs.items.baubles.ItemMiningBackpack;
import hmysjiang.usefulstuffs.items.baubles.ItemStorageBag;
import hmysjiang.usefulstuffs.items.crafting.ItemBuildingWizard;
import hmysjiang.usefulstuffs.items.crafting.ItemCompactStorageUnit;
import hmysjiang.usefulstuffs.items.crafting.ItemFlipFlopCore;
import hmysjiang.usefulstuffs.items.crafting.ItemUmbrella;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
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
	public static Item berry;
	public static Item belt_fiery_lily;
	public static Item backpack;
	public static Item mining_backpack;
	public static Item infinite_water;
	public static Item milk_bag;
	public static Item cheese;
	
	public static void init() {
		water_blacklist = new ItemWaterBlackList();
		bento = new ItemBento();
		building_wand = new ItemBuildingWand();
		building_wand_infinite = new ItemBuildingWandInfinite();
		light_shooter = new ItemLightShooter();
		light_shooter_collecter = new ItemLightShooterCollector();
		packing_glue = new ItemPackingGlue();
		building_wizard = new ItemBuildingWizard();
		compact_storage_unit = new ItemCompactStorageUnit();
		umbrella = new ItemUmbrella();
		belt_lily = new ItemLilyBelt();
		bag_storage = new ItemStorageBag();
		flipflop_core = new ItemFlipFlopCore();
		berry = new ItemBerry();
		belt_fiery_lily = new ItemFieryLilyBelt();
		backpack = new ItemBackpack();
		mining_backpack = new ItemMiningBackpack();
		if (ConfigManager.enableInfiniteWater)
			infinite_water = new ItemInfiniteWater();
		milk_bag = new ItemMilkBag();
		cheese = new ItemCheese();
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
				flipflop_core,
				berry,
				belt_fiery_lily,
				backpack,
				mining_backpack,
				milk_bag,
				cheese);
		if (ConfigManager.enableInfiniteWater)
			event.getRegistry().register(infinite_water);
		
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
		UsefulStuffs.proxy.registerItemRenders(belt_fiery_lily);
		UsefulStuffs.proxy.registerItemRenders(backpack);
		UsefulStuffs.proxy.registerItemRenders(mining_backpack);
		if (ConfigManager.enableInfiniteWater)
			UsefulStuffs.proxy.registerItemRenders(infinite_water);
		UsefulStuffs.proxy.registerItemRenders(milk_bag);
		UsefulStuffs.proxy.registerItemRenders(cheese);
		
		for (EnumBerryColor color: EnumBerryColor.values())
			UsefulStuffs.proxy.registerItemRenders(berry, color.getMetadata(), color.getDyeColorName());
		
		for (Item item: itemblocks)
			UsefulStuffs.proxy.registerItemRenders(item);
	}
	
	private static void register(IForgeRegistry<Item> registry, Item... items) {
		registry.registerAll(items);
		for (Item item: items)
			item.setCreativeTab(UsefulStuffs.TAB);
	}
	
}
