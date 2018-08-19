package hmysjiang.usefulstuffs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ConfigManager {
	
	public static int bushGrowthChance;
	public static int[] bush_banned_biomes;
	public static int[] bush_banned_dims;
	public static int bushSpawnMinHeight;
	public static int playerDetectorRange;
	public static int portalMufflerRange;
	public static int wellTransRate;
	public static int wellTransRange;
	public static boolean bentoSpeed;
	public static int buildingwandDurability;
	public static int buildingwandRange;
	public static int buildingwandRangeInfinite;
	public static int shooterCD;
	public static int glueDurability;
	
	private static Configuration config;
	private static List<String> orderBlock;
	private static List<String> orderItem;
	public static final String FILE_NAME = "usefulstuffs.cfg";
	public static final String CATEGORY_BLOCK = "Blocks";
	public static final String CATEGORY_ITEM = "Items";
	
	public static Configuration getConfig() {
		return config;
	}
	
	public static void loadConfigFile() {	//preInit
		File configfile = new File(Loader.instance().getConfigDir(), FILE_NAME);
		config = new Configuration(configfile);
		loadConfig();
	}
	
	private static void loadConfig() {
		orderBlock = new ArrayList<String>();
		orderItem = new ArrayList<String>();
		config.load();
		
		
		//Blocks
		Property propertyBushGrowthChance = config.get(CATEGORY_BLOCK, "bush_growth_chance", 12);
		propertyBushGrowthChance.setComment(I18n.format("usefulstuffs.config.blocks.bush_growth_chance.comment"));
		propertyBushGrowthChance.setMinValue(7);
		bushGrowthChance = propertyBushGrowthChance.getInt();
		propertyBushGrowthChance.set(bushGrowthChance);
		orderBlock.add(propertyBushGrowthChance.getName());
		
		Property propertyBushBannedBiomes = config.get(CATEGORY_BLOCK, "bush_banned_biomes", new int[] {3, 11, 12, 25, 26, 140, 158});
		propertyBushBannedBiomes.setComment(I18n.format("usefulstuffs.config.blocks.bush_banned_biomes.comment"));
		bush_banned_biomes = propertyBushBannedBiomes.getIntList();
		propertyBushBannedBiomes.set(bush_banned_biomes);
		orderBlock.add(propertyBushBannedBiomes.getName());
		
		Property propertyBushBannedDims = config.get(CATEGORY_BLOCK, "bush_banned_dims", new int[] {1, -1});
		propertyBushBannedDims.setComment(I18n.format("usefulstuffs.config.blocks.bush_banned_dims.comment"));
		bush_banned_dims = propertyBushBannedDims.getIntList();
		propertyBushBannedDims.set(bush_banned_dims);
		orderBlock.add(propertyBushBannedDims.getName());
		
		Property propertyBushSpawnMinH = config.get(CATEGORY_BLOCK, "bush_spawn_min_height", 60);
		propertyBushSpawnMinH.setComment(I18n.format("usefulstuffs.config.blocks.bush_spawn_min_height.comment"));
		propertyBushSpawnMinH.setMinValue(4);
		propertyBushSpawnMinH.setMaxValue(64);
		bushSpawnMinHeight = propertyBushSpawnMinH.getInt();
		propertyBushSpawnMinH.set(bushSpawnMinHeight);
		orderBlock.add(propertyBushSpawnMinH.getName());
		
		Property propertyPlayerDetectorRange = config.get(CATEGORY_BLOCK, "player_detector_range", 2);
		propertyPlayerDetectorRange.setComment(I18n.format("usefulstuffs.config.blocks.player_detector_range.comment"));
		propertyPlayerDetectorRange.setMinValue(2);
		playerDetectorRange = propertyPlayerDetectorRange.getInt();
		propertyPlayerDetectorRange.set(playerDetectorRange);
		orderBlock.add(propertyPlayerDetectorRange.getName());
		
		Property propertyPortalMufflerRange = config.get(CATEGORY_BLOCK, "portal_muffler_range", 4);
		propertyPortalMufflerRange.setComment(I18n.format("usefulstuffs.config.blocks.portal_muffler_range.comment"));
		propertyPortalMufflerRange.setMinValue(4);
		portalMufflerRange = propertyPortalMufflerRange.getInt();
		propertyPortalMufflerRange.set(portalMufflerRange);
		orderBlock.add(propertyPortalMufflerRange.getName());
		
		Property propertyWellTransRate = config.get(CATEGORY_BLOCK, "well_trans_rate", 250);
		propertyWellTransRate.setComment(I18n.format("usefulstuffs.config.blocks.well_trans_rate.comment"));
		propertyWellTransRate.setMaxValue(1000);
		propertyWellTransRate.setMinValue(0);
		wellTransRate = propertyWellTransRate.getInt();
		propertyWellTransRate.set(wellTransRate);
		orderBlock.add(propertyWellTransRate.getName());
		
		Property propertyWellTransRange = config.get(CATEGORY_BLOCK, "well_trans_range", 3);
		propertyWellTransRange.setComment(I18n.format("usefulstuffs.config.blocks.well_trans_range.comment"));
		propertyWellTransRange.setMaxValue(7);
		propertyWellTransRate.setMinValue(0);
		wellTransRange = propertyWellTransRange.getInt();
		propertyWellTransRange.set(wellTransRange);
		orderBlock.add(propertyWellTransRange.getName());
		
		//items
		Property propertyBentoSpeed = config.get(CATEGORY_ITEM, "bento_speed", true);
		propertyBentoSpeed.setComment(I18n.format("usefulstuffs.config.items.bento_speed.comment"));
		bentoSpeed = propertyBentoSpeed.getBoolean();
		propertyBentoSpeed.set(bentoSpeed);
		orderItem.add(propertyBentoSpeed.getName());
		
		Property propertyBuildingWandDurability = config.get(CATEGORY_ITEM, "building_wand_durability", 2048);
		propertyBuildingWandDurability.setComment(I18n.format("usefulstuffs.config.items.building_wand_durability.comment"));
		propertyBuildingWandDurability.setMinValue(0);
		buildingwandDurability = propertyBuildingWandDurability.getInt();
		propertyBuildingWandDurability.set(buildingwandDurability);
		orderItem.add(propertyBuildingWandDurability.getName());
		
		Property propertyBuildingWandRange = config.get(CATEGORY_ITEM, "building_wand_range", 16);
		propertyBuildingWandRange.setComment(I18n.format("usefulstuffs.config.items.building_wand_range.comment"));
		propertyBuildingWandRange.setMinValue(8);
		buildingwandRange = propertyBuildingWandRange.getInt();
		propertyBuildingWandRange.set(buildingwandRange);
		orderItem.add(propertyBuildingWandRange.getName());
		
		Property propertyBuildingWandRangeInfi = config.get(CATEGORY_ITEM, "building_wand_range_infinite", 32);
		propertyBuildingWandRangeInfi.setComment(I18n.format("usefulstuffs.config.items.building_wand_range_infinite.comment"));
		propertyBuildingWandRangeInfi.setMinValue(8);
		buildingwandRangeInfinite = propertyBuildingWandRangeInfi.getInt();
		propertyBuildingWandRangeInfi.set(buildingwandRangeInfinite);
		orderItem.add(propertyBuildingWandRangeInfi.getName());
		
		Property propertyLightShooterCD = config.get(CATEGORY_ITEM, "light_shooter_cooldown", 30);
		propertyLightShooterCD.setComment(I18n.format("usefulstuffs.config.items.light_shooter_cooldown.comment"));
		propertyLightShooterCD.setMinValue(0);
		shooterCD = propertyLightShooterCD.getInt();
		propertyLightShooterCD.set(shooterCD);
		orderItem.add(propertyLightShooterCD.getName());
		
		Property propertyGlueDurability = config.get(CATEGORY_ITEM, "glue_durability", 32);
		propertyGlueDurability.setComment(I18n.format("usefulstuffs.config.items.glue_durability.comment"));
		propertyGlueDurability.setMinValue(32);
		glueDurability = propertyGlueDurability.getInt();
		propertyGlueDurability.set(glueDurability);
		orderItem.add(propertyGlueDurability.getName());
		
		
		config.setCategoryPropertyOrder(CATEGORY_BLOCK, orderBlock);
		config.setCategoryPropertyOrder(CATEGORY_ITEM, orderItem);
		
		
		if (config.hasChanged())
			config.save();
	}

}
