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
	public static int bushSpawnRate;
	public static int playerDetectorRange;
	public static int portalMufflerRange;
	public static int wellTransRate;
	public static int wellTransRange;
	public static boolean campfireNeedsFuel;
	public static boolean bentoSpeed;
	public static int buildingwandDurability;
	public static int buildingwandRange;
	public static int buildingwandRangeInfinite;
	public static int shooterCD;
	public static int glueDurability;
	public static int fierylilySpawnRate;
	
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
		propertyBushGrowthChance.setComment("When growth tick triggers, there is a chance of 1/n that a bush will grow. DEFAULT=12, MIN=7");
		propertyBushGrowthChance.setMinValue(7);
		bushGrowthChance = propertyBushGrowthChance.getInt();
		propertyBushGrowthChance.set(bushGrowthChance);
		orderBlock.add(propertyBushGrowthChance.getName());
		
		Property propertyBushBannedBiomes = config.get(CATEGORY_BLOCK, "bush_banned_biomes", new int[] {3, 11, 12, 25, 26, 140, 158});
		propertyBushBannedBiomes.setComment("Berry bushes will not generate in these biomes");
		bush_banned_biomes = propertyBushBannedBiomes.getIntList();
		propertyBushBannedBiomes.set(bush_banned_biomes);
		orderBlock.add(propertyBushBannedBiomes.getName());
		
		Property propertyBushBannedDims = config.get(CATEGORY_BLOCK, "bush_banned_dims", new int[] {1, -1});
		propertyBushBannedDims.setComment("Berry bushes will not generate in these dimensions");
		bush_banned_dims = propertyBushBannedDims.getIntList();
		propertyBushBannedDims.set(bush_banned_dims);
		orderBlock.add(propertyBushBannedDims.getName());
		
		Property propertyBushSpawnMinH = config.get(CATEGORY_BLOCK, "bush_spawn_min_height", 60);
		propertyBushSpawnMinH.setComment("The minimum height that a bush will generate on. DEFAULT=60, MIN=4, MAX=64");
		propertyBushSpawnMinH.setMinValue(4);
		propertyBushSpawnMinH.setMaxValue(64);
		bushSpawnMinHeight = propertyBushSpawnMinH.getInt();
		propertyBushSpawnMinH.set(bushSpawnMinHeight);
		orderBlock.add(propertyBushSpawnMinH.getName());
		
		Property propertyBushSpawnRate = config.get(CATEGORY_BLOCK, "bush_spawn_rate", 50);
		propertyBushSpawnRate.setComment("There is a chance of 1/n that a group of bushes will spawn in a chunk with proper biome, set to 0 to disable bush gen. DEFAULT=50");
		propertyBushSpawnRate.setMinValue(0);
		bushSpawnRate = propertyBushSpawnRate.getInt();
		propertyBushSpawnRate.set(bushSpawnRate);
		orderBlock.add(propertyBushSpawnRate.getName());
		
		Property propertyPlayerDetectorRange = config.get(CATEGORY_BLOCK, "player_detector_range", 2);
		propertyPlayerDetectorRange.setComment("The radius of the player detector. DEFAULT=2, MIN=2");
		propertyPlayerDetectorRange.setMinValue(2);
		playerDetectorRange = propertyPlayerDetectorRange.getInt();
		propertyPlayerDetectorRange.set(playerDetectorRange);
		orderBlock.add(propertyPlayerDetectorRange.getName());
		
		Property propertyPortalMufflerRange = config.get(CATEGORY_BLOCK, "portal_muffler_range", 4);
		propertyPortalMufflerRange.setComment("The radius which a portal muffler can reach. DEFAULT=4, MIN=4");
		propertyPortalMufflerRange.setMinValue(4);
		portalMufflerRange = propertyPortalMufflerRange.getInt();
		propertyPortalMufflerRange.set(portalMufflerRange);
		orderBlock.add(propertyPortalMufflerRange.getName());
		
		Property propertyWellTransRate = config.get(CATEGORY_BLOCK, "well_trans_rate", 250);
		propertyWellTransRate.setComment("The transfer rate of the well (mB/side*tick), set to 0 to disable. DEFAULT=250, MAX=1000");
		propertyWellTransRate.setMaxValue(1000);
		propertyWellTransRate.setMinValue(0);
		wellTransRate = propertyWellTransRate.getInt();
		propertyWellTransRate.set(wellTransRate);
		orderBlock.add(propertyWellTransRate.getName());
		
		Property propertyWellTransRange = config.get(CATEGORY_BLOCK, "well_trans_range", 3);
		propertyWellTransRange.setComment("The range of the well. DEFAULT=3, MIN=0, MAX=7");
		propertyWellTransRange.setMaxValue(7);
		propertyWellTransRate.setMinValue(0);
		wellTransRange = propertyWellTransRange.getInt();
		propertyWellTransRange.set(wellTransRange);
		orderBlock.add(propertyWellTransRange.getName());
		
		Property propertyCampfireFuel = config.get(CATEGORY_BLOCK, "campfire_needs_fuel", true);
		propertyCampfireFuel.setComment("Does campfire requires fuel to work? DEFAULT=true");
		campfireNeedsFuel = propertyCampfireFuel.getBoolean();
		propertyCampfireFuel.set(campfireNeedsFuel);
		orderBlock.add(propertyCampfireFuel.getName());
		
		Property propertyFieryLilySpawnRate = config.get(CATEGORY_BLOCK, "fierylily_spawn_rate", 50);
		propertyFieryLilySpawnRate.setComment("There is a chance of 1/n that fierylily will spawn in a chunk, set to 0 to disable fierylily gen. DEFAULT=40");
		propertyFieryLilySpawnRate.setMinValue(0);
		fierylilySpawnRate = propertyFieryLilySpawnRate.getInt();
		propertyFieryLilySpawnRate.set(fierylilySpawnRate);
		orderBlock.add(propertyFieryLilySpawnRate.getName());
		
		//items
		Property propertyBentoSpeed = config.get(CATEGORY_ITEM, "bento_speed", true);
		propertyBentoSpeed.setComment("Should the max using duration of bento simulates its content? DEFAULT=true");
		bentoSpeed = propertyBentoSpeed.getBoolean();
		propertyBentoSpeed.set(bentoSpeed);
		orderItem.add(propertyBentoSpeed.getName());
		
		Property propertyBuildingWandDurability = config.get(CATEGORY_ITEM, "building_wand_durability", 2048);
		propertyBuildingWandDurability.setComment("The durability of a normal building wand. DEFAULT=2048, MIN=0");
		propertyBuildingWandDurability.setMinValue(0);
		buildingwandDurability = propertyBuildingWandDurability.getInt();
		propertyBuildingWandDurability.set(buildingwandDurability);
		orderItem.add(propertyBuildingWandDurability.getName());
		
		Property propertyBuildingWandRange = config.get(CATEGORY_ITEM, "building_wand_range", 16);
		propertyBuildingWandRange.setComment("The range of a normal building wand. DEFAULT=16, MIN=8");
		propertyBuildingWandRange.setMinValue(8);
		buildingwandRange = propertyBuildingWandRange.getInt();
		propertyBuildingWandRange.set(buildingwandRange);
		orderItem.add(propertyBuildingWandRange.getName());
		
		Property propertyBuildingWandRangeInfi = config.get(CATEGORY_ITEM, "building_wand_range_infinite", 32);
		propertyBuildingWandRangeInfi.setComment("The range of a infinite building wand. DEFAULT=32, MIN=8");
		propertyBuildingWandRangeInfi.setMinValue(8);
		buildingwandRangeInfinite = propertyBuildingWandRangeInfi.getInt();
		propertyBuildingWandRangeInfi.set(buildingwandRangeInfinite);
		orderItem.add(propertyBuildingWandRangeInfi.getName());
		
		Property propertyLightShooterCD = config.get(CATEGORY_ITEM, "light_shooter_cooldown", 30);
		propertyLightShooterCD.setComment("The cooldown of light shooter, set to 0 to disable cooldown. DEFAULT=30");
		propertyLightShooterCD.setMinValue(0);
		shooterCD = propertyLightShooterCD.getInt();
		propertyLightShooterCD.set(shooterCD);
		orderItem.add(propertyLightShooterCD.getName());
		
		Property propertyGlueDurability = config.get(CATEGORY_ITEM, "glue_durability", 32);
		propertyGlueDurability.setComment("The durability of packing glue. DEFAULT=32, MIN=32");
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
