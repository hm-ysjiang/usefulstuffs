package hmysjiang.usefulstuffs.init;

import java.util.ArrayList;
import java.util.List;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.blocks.bush.EnumBerryColor;
import hmysjiang.usefulstuffs.items.ItemBento;
import hmysjiang.usefulstuffs.items.ItemBerry;
import hmysjiang.usefulstuffs.items.ItemBuildingWand;
import hmysjiang.usefulstuffs.items.ItemBuildingWandInfinite;
import hmysjiang.usefulstuffs.items.ItemCheese;
import hmysjiang.usefulstuffs.items.ItemInfiniteWater;
import hmysjiang.usefulstuffs.items.ItemLightBattery;
import hmysjiang.usefulstuffs.items.ItemLightBow;
import hmysjiang.usefulstuffs.items.ItemLightShooter;
import hmysjiang.usefulstuffs.items.ItemLightShooterCollector;
import hmysjiang.usefulstuffs.items.ItemMilkBag;
import hmysjiang.usefulstuffs.items.ItemPackingGlue;
import hmysjiang.usefulstuffs.items.ItemWaterBlackList;
import hmysjiang.usefulstuffs.items.baubles.ItemBackpack;
import hmysjiang.usefulstuffs.items.baubles.ItemFieryLilyBelt;
import hmysjiang.usefulstuffs.items.baubles.ItemLilyBelt;
import hmysjiang.usefulstuffs.items.baubles.ItemMiningBackpack;
import hmysjiang.usefulstuffs.items.baubles.ItemPotatoCharm;
import hmysjiang.usefulstuffs.items.baubles.ItemStabilizingRing;
import hmysjiang.usefulstuffs.items.baubles.ItemStorageBag;
import hmysjiang.usefulstuffs.items.crafting.ItemCraftingIngredient;
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
	public static Item light_bow;
	public static Item light_battery;
	public static Item triangular_prism;
	public static Item universal_core;
	public static Item charm_potato;
	public static Item ring_stablizing;
	
	public static void init() {
		water_blacklist = new ItemWaterBlackList();
		bento = new ItemBento();
		building_wand = new ItemBuildingWand();
		building_wand_infinite = new ItemBuildingWandInfinite();
		light_shooter = new ItemLightShooter();
		light_shooter_collecter = new ItemLightShooterCollector();
		packing_glue = new ItemPackingGlue();
		building_wizard = new ItemCraftingIngredient(Reference.ModItems.BUILDING_WIZARD.getRegistryName(), Reference.ModItems.BUILDING_WIZARD.getUnlocalizedName());
		compact_storage_unit = new ItemCraftingIngredient(Reference.ModItems.CSU.getRegistryName(), Reference.ModItems.CSU.getUnlocalizedName());
		umbrella = new ItemUmbrella();
		belt_lily = new ItemLilyBelt();
		bag_storage = new ItemStorageBag();
		flipflop_core = new ItemCraftingIngredient(Reference.ModItems.FLIPFLOPCORE.getRegistryName(), Reference.ModItems.FLIPFLOPCORE.getUnlocalizedName());
		berry = new ItemBerry();
		belt_fiery_lily = new ItemFieryLilyBelt();
		backpack = new ItemBackpack();
		mining_backpack = new ItemMiningBackpack();
		infinite_water = new ItemInfiniteWater();
		milk_bag = new ItemMilkBag();
		cheese = new ItemCheese();
		light_bow = new ItemLightBow();
		light_battery = new ItemLightBattery();
		triangular_prism = new ItemCraftingIngredient(Reference.ModItems.TRIANGULAR_PRISM.getRegistryName(), Reference.ModItems.TRIANGULAR_PRISM.getUnlocalizedName());
		universal_core = new ItemCraftingIngredient(Reference.ModItems.UNIVERSAL_CORE.getRegistryName(), Reference.ModItems.UNIVERSAL_CORE.getUnlocalizedName());
		charm_potato = new ItemPotatoCharm();
		ring_stablizing = new ItemStabilizingRing();
	}
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		if (ConfigManager.wellEnabled) {
			registry.register(water_blacklist);
		}
		if (ConfigManager.bentoEnabled) {
			registry.register(bento);
		}
		if (ConfigManager.buildingWandsEnabled) {
			registry.register(building_wand);
			registry.register(building_wand_infinite);
			registry.register(building_wizard);
		}
		if (ConfigManager.lightShootersEnabled) {
			registry.register(light_shooter);
			registry.register(light_shooter_collecter);
		}
		if (ConfigManager.glueEnabled) {
			registry.register(packing_glue);
		}
		if (ConfigManager.lilyBeltEnabled) {
			registry.register(belt_lily);
		}
		if (ConfigManager.bagStorageEnabled) {
			registry.register(bag_storage);
		}
		if (ConfigManager.tFlipFlopEnabled) {
			registry.register(flipflop_core);
		}
		if (ConfigManager.berryEnabled) {
			registry.register(berry);
		}
		if (ConfigManager.fieryLilyEnabled) {
			registry.register(belt_fiery_lily);
		}
		if (ConfigManager.backpackEnabled) {
			registry.register(backpack);
		}
		if (ConfigManager.miningBackpackEnabled) {
			registry.register(mining_backpack);
		}
		if (ConfigManager.infiniteWaterEnabled) {
			registry.register(infinite_water);
		}
		if (ConfigManager.milkBagEnabled) {
			registry.register(milk_bag);
		}
		if (ConfigManager.milkFermenterEnabled) {
			registry.register(cheese);
		}
		if (ConfigManager.lightBowEnabled) {
			registry.register(light_bow);
		}
		if (ConfigManager.lightBatteryEnabled) {
			registry.register(light_battery);
		}
		if (ConfigManager.universalUserEnabled) {
			registry.register(universal_core);
		}
		if (ConfigManager.charmPotatoEnabled) {
			registry.register(charm_potato);
		}
		if (ConfigManager.stabilizingRingEnabled) {
			registry.register(ring_stablizing);
		}
		register(registry, 
				compact_storage_unit,
				umbrella,
				triangular_prism);
		
		registry.registerAll(itemblocks.toArray(new Item[0]));
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
		UsefulStuffs.proxy.registerItemRenders(infinite_water);
		UsefulStuffs.proxy.registerItemRenders(milk_bag);
		UsefulStuffs.proxy.registerItemRenders(cheese);
		UsefulStuffs.proxy.registerItemRenders(light_bow);
		UsefulStuffs.proxy.registerItemRenders(light_battery);
		UsefulStuffs.proxy.registerItemRenders(triangular_prism);
		UsefulStuffs.proxy.registerItemRenders(universal_core);
		UsefulStuffs.proxy.registerItemRenders(charm_potato);
		UsefulStuffs.proxy.registerItemRenders(ring_stablizing);
		
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
