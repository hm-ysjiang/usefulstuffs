package hmysjiang.usefulstuffs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;

public class ConfigManager {
	
	private static Configuration config;
	private static List<String> orderBlock;
	private static List<String> orderItem;
	private static List<String> orderGeneral;
	private static List<String> orderEnable;
	public static final String FILE_NAME = "usefulstuffs.cfg";
	public static final String CATEGORY_BLOCK = "Blocks";
	public static final String CATEGORY_ITEM = "Items";
	public static final String CATEGORY_GENERAL = "General";
	public static final String CATEGORY_ENABLE = "Enable";
	
	//Blocks
	public static int bushGrowthChance;
	public static int[] bush_banned_biomes;
	public static int[] bush_banned_dims;
	public static int bushSpawnMinHeight;
	public static int bushSpawnRate;
	public static boolean bushDropsAfterMature;
	public static int playerDetectorRange;
	public static int portalMufflerRange;
	public static int wellTransRate;
	public static int wellTransRange;
	public static boolean enableCampfireCraft;
	public static boolean campfireNeedsFuel;
	public static boolean doPlacementCheck;
	public static int milkFermentTime;
	public static boolean onlyDetectOneSide;
	
	//Items
	public static boolean bentoSpeed;
	public static int buildingwandDurability;
	public static int buildingwandRange;
	public static int buildingwandRangeInfinite;
	public static int shooterCD;
	public static int glueDurability;
	public static String[] glueBlackList;
	public static int fierylilySpawnRate;
	public static boolean fermentedMilkCauseNegativeEffect;
	public static boolean cheeseDoesBuff;
	public static boolean shooterAcceptBattery;
	public static int shooterUseBattery;
	
	//General
	public static boolean chickenDropsFeather;
	
	//Enable
	public static boolean potionLilyEnabled;
	public static boolean potionFieryLilyEnabled;
	public static boolean potionParachuteEnabled;
	public static boolean campfireEnabled;
	public static boolean filingCabinetUnstackableEnabled;
	public static boolean glueEnabled;
	public static boolean lightBulbEnabled;
	public static boolean rainDetectorEnabled;
	public static boolean tFlipFlopEnabled;
	public static boolean wellEnabled;
	public static boolean berryEnabled;
	public static boolean portalMufflerEnabled;
	public static boolean playerDetectorEnabled;
	public static boolean fieryLilyEnabled;
	public static boolean milkFermenterEnabled;
	public static boolean universalUserEnabled;
	public static boolean bentoEnabled;
	public static boolean buildingWandsEnabled;
	public static boolean lightShootersEnabled;
	public static boolean lilyBeltEnabled;
	public static boolean bagStorageEnabled;
	public static boolean backpackEnabled;
	public static boolean miningBackpackEnabled;
	public static boolean infiniteWaterEnabled;
	public static boolean milkBagEnabled;
	public static boolean lightBowEnabled;
	public static boolean lightBatteryEnabled;
	public static boolean filingCabinetNbtEnabled;
	public static boolean charmPotatoEnabled;
	public static boolean stabilizingRingEnabled;
	public static boolean tankItemEnabled;
	public static boolean tankBlockEnabled;
	
	
	public static Configuration getConfig() {
		return config;
	}
	
	public static void loadConfigFile() {
		File configfile = new File(Loader.instance().getConfigDir(), FILE_NAME);
		config = new Configuration(configfile);
		loadConfig();
	}
	
	private static void loadConfig() {
		orderBlock = new ArrayList<String>();
		orderItem = new ArrayList<String>();
		orderGeneral = new ArrayList<String>();
		orderEnable = new ArrayList<String>();
		config.load();
		
		loadEnableProperties();
		loadBlocksProperties();
		loadItemsProperties();
		loadGeneralProperties();
		
		config.setCategoryPropertyOrder(CATEGORY_BLOCK, orderBlock);
		config.setCategoryPropertyOrder(CATEGORY_ITEM, orderItem);
		config.setCategoryPropertyOrder(CATEGORY_GENERAL, orderGeneral);
		config.setCategoryPropertyOrder(CATEGORY_ENABLE, orderEnable);
		
		if (config.hasChanged())
			config.save();
	}
	
	private static void loadBlocksProperties() {
		Property propertyBushGrowthChance = config.get(CATEGORY_BLOCK, "bush_growth_chance", 12);
		propertyBushGrowthChance.setComment("When growth tick triggers, there is a chance of 1/n that a bush will grow. DEFAULT=12, MIN=7");
		propertyBushGrowthChance.setMinValue(7);
		bushGrowthChance = propertyBushGrowthChance.getInt();
		propertyBushGrowthChance.set(bushGrowthChance);
		orderBlock.add(propertyBushGrowthChance.getName());
		
		Property propertyBushBannedBiomes = config.get(CATEGORY_BLOCK, "bush_banned_biome_ids", new int[] {3, 11, 12, 25, 26, 140, 158});
		propertyBushBannedBiomes.setComment("Berry bushes will not generate in these biomes");
		bush_banned_biomes = propertyBushBannedBiomes.getIntList();
		propertyBushBannedBiomes.set(bush_banned_biomes);
		orderBlock.add(propertyBushBannedBiomes.getName());
		
		Property propertyBushBannedDims = config.get(CATEGORY_BLOCK, "bush_banned_dim_ids", new int[] {1, -1});
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
		propertyBushSpawnRate.setComment("There is a chance of 1/n that a group of bushes will spawn in a chunk with a proper biome, set to 0 to disable bush gen. DEFAULT=50");
		propertyBushSpawnRate.setMinValue(0);
		bushSpawnRate = propertyBushSpawnRate.getInt();
		propertyBushSpawnRate.set(bushSpawnRate);
		orderBlock.add(propertyBushSpawnRate.getName());
		
		Property propertyBushDropMature = config.get(CATEGORY_BLOCK, "bush_drop_mature", true);
		propertyBushDropMature.setComment("If this is set to true, a mature bush will drop a berry on the ground at grow tick (Note that the entity spawn will have a shorter lifespan). DEFAULT=true");
		bushDropsAfterMature = propertyBushDropMature.getBoolean();
		propertyBushDropMature.set(bushDropsAfterMature);
		orderBlock.add(propertyBushDropMature.getName());
		
		Property propertyPlayerDetectorRange = config.get(CATEGORY_BLOCK, "player_detector_range", 1);
		propertyPlayerDetectorRange.setComment("The radius of the player detector. DEFAULT=1, MIN=1");
		propertyPlayerDetectorRange.setMinValue(1);
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
		
		Property propertyCampfireCraft = config.get(CATEGORY_BLOCK, "campfire_craftable", true);
		propertyCampfireCraft.setComment("Is the campfire craftable? DEFAULT=true, REQUIRE enable_campfire to be true");
		enableCampfireCraft = propertyCampfireCraft.getBoolean() && campfireEnabled;
		propertyCampfireCraft.set(enableCampfireCraft);
		orderBlock.add(propertyCampfireCraft.getName());
		
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
		
		Property propertyDoPlacementCheck = config.get(CATEGORY_BLOCK, "glued_box_placement_check", true);
		propertyDoPlacementCheck.setComment("Should the glued box check for the placement logic when being opened? DEFAULT=true NOTE:This may behave strangely on non-full blocks");
		doPlacementCheck = propertyDoPlacementCheck.getBoolean();
		propertyDoPlacementCheck.set(doPlacementCheck);
		orderBlock.add(propertyDoPlacementCheck.getName());
		
		Property propertyGlueBlackList = config.get(CATEGORY_BLOCK, "glue_blacklist", new String[] {"minecraft:dragon_egg"});
		propertyGlueBlackList.setComment("Blocks that packing glue cannot pick up, format: \"<modname>:<blockname>\"");
		glueBlackList = propertyGlueBlackList.getStringList();
		propertyGlueBlackList.set(glueBlackList);
		orderBlock.add(propertyGlueBlackList.getName());
		
		Property propertyMilkFermentTime = config.get(CATEGORY_BLOCK, "milk_ferment_time", 6000);
		propertyMilkFermentTime.setComment("How many ticks does it take to ferment a bucket of milk into cheese? DEFAULT=6000");
		propertyMilkFermentTime.setMinValue(0);
		milkFermentTime = propertyMilkFermentTime.getInt();
		milkFermentTime -=milkFermentTime%100;
		propertyMilkFermentTime.set(milkFermentTime);
		orderBlock.add(propertyMilkFermentTime.getName());
		
		Property propertyOnlyDetectOneSide = config.get(CATEGORY_BLOCK, "only_detect_1_side", true);
		propertyOnlyDetectOneSide.setComment("Should the Player Detector only detect the side it is facing? DEFAULT=true");
		onlyDetectOneSide = propertyOnlyDetectOneSide.getBoolean();
		propertyOnlyDetectOneSide.set(onlyDetectOneSide);
		orderBlock.add(propertyOnlyDetectOneSide.getName());
	}
	
	private static void loadItemsProperties() {
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
		
		Property propertyFermentedMilkNegativeEffect = config.get(CATEGORY_ITEM, "fermented_milk_negative_effect", true);
		propertyFermentedMilkNegativeEffect.setComment("Does drinking a bucket of partially fermented milk causes negative effect? DEFAUT=true");
		fermentedMilkCauseNegativeEffect = propertyFermentedMilkNegativeEffect.getBoolean();
		propertyFermentedMilkNegativeEffect.set(fermentedMilkCauseNegativeEffect);
		orderItem.add(propertyFermentedMilkNegativeEffect.getName());
		
		Property propertyCheeseDoesBuff = config.get(CATEGORY_ITEM, "cheese_does_buff", true);
		propertyCheeseDoesBuff.setComment("Does cheese buffs the player? DEFAUT=true");
		cheeseDoesBuff = propertyCheeseDoesBuff.getBoolean();
		propertyCheeseDoesBuff.set(cheeseDoesBuff);
		orderItem.add(propertyCheeseDoesBuff.getName());
		
		Property propertyShooterAccpetBattery = config.get(CATEGORY_ITEM, "shooter_accpet_battery", false);
		propertyShooterAccpetBattery.setComment("Does the light shooter accepts charges from light battery? DEFAUT=false");
		shooterAcceptBattery = propertyShooterAccpetBattery.getBoolean();
		propertyShooterAccpetBattery.set(shooterAcceptBattery);
		orderItem.add(propertyShooterAccpetBattery.getName());
		
		Property propertyShooterUseBattery = config.get(CATEGORY_ITEM, "shooter_use_battery", 250);
		propertyShooterUseBattery.setComment("If the light shooter accepts charges from battery, how much does it cost to launch an ammo? DEFAUT=250");
		shooterUseBattery = propertyShooterUseBattery.getInt();
		propertyShooterUseBattery.set(shooterUseBattery);
		orderItem.add(propertyShooterUseBattery.getName());
	}
	
	private static void loadGeneralProperties() {
		Property propertyChickenDropsFeather = config.get(CATEGORY_GENERAL, "chicken_drops_feather", true);
		propertyChickenDropsFeather.setComment("Does chicken drops feather while falling? DEFAULT=true");
		chickenDropsFeather = propertyChickenDropsFeather.getBoolean();
		propertyChickenDropsFeather.set(chickenDropsFeather);
		orderGeneral.add(propertyChickenDropsFeather.getName());
	}
	
	private static void loadEnableProperties() {		
		Property propertyPotionLilyEnabled = config.get(CATEGORY_ENABLE, "enable_potion_lily", true);
		propertyPotionLilyEnabled.setComment("Enable Potion Effect Li-ly?");
		potionLilyEnabled = propertyPotionLilyEnabled.getBoolean();
		propertyPotionLilyEnabled.set(potionLilyEnabled);
		orderEnable.add(propertyPotionLilyEnabled.getName());
		
		Property propertyParachuteEnabled = config.get(CATEGORY_ENABLE, "enable_potion_parachute", true);
		propertyParachuteEnabled.setComment("Enable Potion Effect Parachute?");
		potionParachuteEnabled = propertyParachuteEnabled.getBoolean();
		propertyParachuteEnabled.set(potionParachuteEnabled);
		orderEnable.add(propertyParachuteEnabled.getName());
		
		Property propertyCampfireEnabled = config.get(CATEGORY_ENABLE, "enable_campfire", true);
		propertyCampfireEnabled.setComment("Enable Campfire of Traverller's Wisdom?");
		campfireEnabled = propertyCampfireEnabled.getBoolean();
		propertyCampfireEnabled.set(campfireEnabled);
		orderEnable.add(propertyCampfireEnabled.getName());
		
		Property propertyFilingCabinetUnstackableEnabled = config.get(CATEGORY_ENABLE, "enable_unstackable_filing_cabinet", true);
		propertyFilingCabinetUnstackableEnabled.setComment("Enable Unstackable Filing Cabinet?");
		filingCabinetUnstackableEnabled = propertyFilingCabinetUnstackableEnabled.getBoolean();
		propertyFilingCabinetUnstackableEnabled.set(filingCabinetUnstackableEnabled);
		orderEnable.add(propertyFilingCabinetUnstackableEnabled.getName());
		
		Property propertyFilingCabinetNbtEnabled = config.get(CATEGORY_ENABLE, "enable_nbt_filing_cabinet", true);
		propertyFilingCabinetNbtEnabled.setComment("Enable NBT Filing Cabinet?");
		filingCabinetNbtEnabled = propertyFilingCabinetNbtEnabled.getBoolean();
		propertyFilingCabinetNbtEnabled.set(filingCabinetNbtEnabled);
		orderEnable.add(propertyFilingCabinetNbtEnabled.getName());
		
		Property propertyGlueEnabled = config.get(CATEGORY_ENABLE, "enable_glued", true);
		propertyGlueEnabled.setComment("Enable Packing Glue and Glued Box?");
		glueEnabled = propertyGlueEnabled.getBoolean();
		propertyGlueEnabled.set(glueEnabled);
		orderEnable.add(propertyGlueEnabled.getName());
		
		Property propertyLightBulbEnabled = config.get(CATEGORY_ENABLE, "enable_light_bulb", true);
		propertyLightBulbEnabled.setComment("Enable Light Bulb?");
		lightBulbEnabled = propertyLightBulbEnabled.getBoolean();
		propertyLightBulbEnabled.set(lightBulbEnabled);
		orderEnable.add(propertyLightBulbEnabled.getName());
		
		Property propertyRainDetectorEnabled = config.get(CATEGORY_ENABLE, "enable_rain_detector", true);
		propertyRainDetectorEnabled.setComment("Enable Rain Detector?");
		rainDetectorEnabled = propertyRainDetectorEnabled.getBoolean();
		propertyRainDetectorEnabled.set(rainDetectorEnabled);
		orderEnable.add(propertyRainDetectorEnabled.getName());
		
		Property propertyTFlipFlopEnabled = config.get(CATEGORY_ENABLE, "enable_t_flipflop", true);
		propertyTFlipFlopEnabled.setComment("Enable T FlipFlop?");
		tFlipFlopEnabled = propertyTFlipFlopEnabled.getBoolean();
		propertyTFlipFlopEnabled.set(tFlipFlopEnabled);
		orderEnable.add(propertyTFlipFlopEnabled.getName());
		
		Property propertyWellEnabled = config.get(CATEGORY_ENABLE, "enable_well", true);
		propertyWellEnabled.setComment("Enable Well of Waterfall?");
		wellEnabled = propertyWellEnabled.getBoolean();
		propertyWellEnabled.set(wellEnabled);
		orderEnable.add(propertyWellEnabled.getName());
		
		Property propertyBerryEnabled = config.get(CATEGORY_ENABLE, "enable_berries", true);
		propertyBerryEnabled.setComment("Enable Berries and Berry Bushes?");
		berryEnabled = propertyBerryEnabled.getBoolean();
		propertyBerryEnabled.set(berryEnabled);
		orderEnable.add(propertyBerryEnabled.getName());
		
		Property propertyPortalMufflerEnabled = config.get(CATEGORY_ENABLE, "enable_portal_muffler", true);
		propertyPortalMufflerEnabled.setComment("Enable Portal Muffler?");
		portalMufflerEnabled = propertyPortalMufflerEnabled.getBoolean();
		propertyPortalMufflerEnabled.set(portalMufflerEnabled);
		orderEnable.add(propertyPortalMufflerEnabled.getName());
		
		Property propertyPlayerDetectorEnabled = config.get(CATEGORY_ENABLE, "enable_player_detector", true);
		propertyPlayerDetectorEnabled.setComment("Enable Player Detector?");
		playerDetectorEnabled = propertyPlayerDetectorEnabled.getBoolean();
		propertyPlayerDetectorEnabled.set(playerDetectorEnabled);
		orderEnable.add(propertyPlayerDetectorEnabled.getName());
		
		Property propertyFieryLilyEnabled = config.get(CATEGORY_ENABLE, "enable_fiery_lily", true);
		propertyFieryLilyEnabled.setComment("Enable Fiery Lily and Belt of Fiery Lily?");
		fieryLilyEnabled = propertyFieryLilyEnabled.getBoolean();
		propertyFieryLilyEnabled.set(fieryLilyEnabled);
		orderEnable.add(propertyFieryLilyEnabled.getName());
		
		Property propertyPotionFieryLilyEnabled = config.get(CATEGORY_ENABLE, "enable_potion_fiery_lily", true);
		propertyPotionFieryLilyEnabled.setComment("Enable Potion Effect Fiery Li-ly? DEFAULT=true, REQUIRE enable_fiery_lily to be true");
		potionFieryLilyEnabled = propertyPotionFieryLilyEnabled.getBoolean() && fieryLilyEnabled;
		propertyPotionFieryLilyEnabled.set(potionFieryLilyEnabled);
		orderEnable.add(propertyPotionFieryLilyEnabled.getName());
		
		Property propertyMilkBagEnabled = config.get(CATEGORY_ENABLE, "enable_milk_bag", true);
		propertyMilkBagEnabled.setComment("Enable Milk Bag?");
		milkBagEnabled = propertyMilkBagEnabled.getBoolean();
		propertyMilkBagEnabled.set(milkBagEnabled);
		orderEnable.add(propertyMilkBagEnabled.getName());
		
		Property propertyMilkFermenterEnabled = config.get(CATEGORY_ENABLE, "enable_milk_fermenter", true);
		propertyMilkFermenterEnabled.setComment("Enable Milk Fermenter? DEFAULT=true, REQUIRE enable_milk_bag to be true");
		milkFermenterEnabled = propertyMilkFermenterEnabled.getBoolean() && milkBagEnabled;
		propertyMilkFermenterEnabled.set(milkFermenterEnabled);
		orderEnable.add(propertyMilkFermenterEnabled.getName());
		
		Property propertyUniversalUserEnabled = config.get(CATEGORY_ENABLE, "enable_universal_user", true);
		propertyUniversalUserEnabled.setComment("Enable Universal User?");
		universalUserEnabled = propertyUniversalUserEnabled.getBoolean();
		propertyUniversalUserEnabled.set(universalUserEnabled);
		orderEnable.add(propertyUniversalUserEnabled.getName());
		
		Property propertyBentoEnabled = config.get(CATEGORY_ENABLE, "enable_bento_box", true);
		propertyBentoEnabled.setComment("Enable Bento Box?");
		bentoEnabled = propertyBentoEnabled.getBoolean();
		propertyBentoEnabled.set(bentoEnabled);
		orderEnable.add(propertyBentoEnabled.getName());
		
		Property propertyBuildingWandsEnabled = config.get(CATEGORY_ENABLE, "enable_building_wands", true);
		propertyBuildingWandsEnabled.setComment("Enable Building Wands?");
		buildingWandsEnabled = propertyBuildingWandsEnabled.getBoolean();
		propertyBuildingWandsEnabled.set(buildingWandsEnabled);
		orderEnable.add(propertyBuildingWandsEnabled.getName());
		
		Property propertyLightShootersEnabled = config.get(CATEGORY_ENABLE, "enable_light_shooters", true);
		propertyLightShootersEnabled.setComment("Enable Light Shooters? DEFAULT=true, REQUIRE enable_light_blub to be true");
		lightShootersEnabled = propertyLightShootersEnabled.getBoolean() && lightBulbEnabled;
		propertyLightShootersEnabled.set(lightShootersEnabled);
		orderEnable.add(propertyLightShootersEnabled.getName());
		
		Property propertyLiliBeltEnabled = config.get(CATEGORY_ENABLE, "enable_lily_belt", true);
		propertyLiliBeltEnabled.setComment("Enable Lily Belt?");
		lilyBeltEnabled = propertyLiliBeltEnabled.getBoolean();
		propertyLiliBeltEnabled.set(lilyBeltEnabled);
		orderEnable.add(propertyLiliBeltEnabled.getName());
		
		Property propertyBagStorageEnabled = config.get(CATEGORY_ENABLE, "enable_storage_body", true);
		propertyBagStorageEnabled.setComment("Enable Storage Bag?");
		bagStorageEnabled = propertyBagStorageEnabled.getBoolean();
		propertyBagStorageEnabled.set(bagStorageEnabled);
		orderEnable.add(propertyBagStorageEnabled.getName());
		
		Property propertyBackpackEnabled = config.get(CATEGORY_ENABLE, "enable_backpack", true);
		propertyBackpackEnabled.setComment("Enable Backpack?");
		backpackEnabled = propertyBackpackEnabled.getBoolean();
		propertyBackpackEnabled.set(backpackEnabled);
		orderEnable.add(propertyBackpackEnabled.getName());
		
		Property propertyMiningBackpackEnabled = config.get(CATEGORY_ENABLE, "enable_mining_backpack", true);
		propertyMiningBackpackEnabled.setComment("Enable Mining Backpack?");
		miningBackpackEnabled = propertyMiningBackpackEnabled.getBoolean();
		propertyMiningBackpackEnabled.set(miningBackpackEnabled);
		orderEnable.add(propertyMiningBackpackEnabled.getName());
		
		Property propertyInfiniteWaterEnabled = config.get(CATEGORY_ENABLE, "enable_infinite_water", true);
		propertyInfiniteWaterEnabled.setComment("Enable Infinite Water (Item)?");
		infiniteWaterEnabled = propertyInfiniteWaterEnabled.getBoolean();
		propertyInfiniteWaterEnabled.set(infiniteWaterEnabled);
		orderEnable.add(propertyInfiniteWaterEnabled.getName());
		
		Property propertyLightBowEnabled = config.get(CATEGORY_ENABLE, "enable_light_bow", true);
		propertyLightBowEnabled.setComment("Enable Light Bow?");
		lightBowEnabled = propertyLightBowEnabled.getBoolean();
		propertyLightBowEnabled.set(lightBowEnabled);
		orderEnable.add(propertyLightBowEnabled.getName());
		
		Property propertyLightBatteryEnabled = config.get(CATEGORY_ENABLE, "enable_light_battery", true);
		propertyLightBatteryEnabled.setComment("Enable Light Battery?");
		lightBatteryEnabled = propertyLightBatteryEnabled.getBoolean();
		propertyLightBatteryEnabled.set(lightBatteryEnabled);
		orderEnable.add(propertyLightBatteryEnabled.getName());
		
		Property propertyPotatoCharmEnabled = config.get(CATEGORY_ENABLE, "enable_potato_charm", false);
		propertyPotatoCharmEnabled.setComment("Enable Charm of Potato (WIP)? DEFAULT=false");
		charmPotatoEnabled = propertyPotatoCharmEnabled.getBoolean();
		propertyPotatoCharmEnabled.set(charmPotatoEnabled);
		orderEnable.add(propertyPotatoCharmEnabled.getName());
		
		Property propertyStabilizingRingEnabled = config.get(CATEGORY_ENABLE, "enable_stabilizing_ring", true);
		propertyStabilizingRingEnabled.setComment("Enable Ring of Stabilizing? DEFAULT=true");
		stabilizingRingEnabled = propertyStabilizingRingEnabled.getBoolean();
		propertyStabilizingRingEnabled.set(stabilizingRingEnabled);
		orderEnable.add(propertyStabilizingRingEnabled.getName());
		
		Property propertyTankItemEnabled = config.get(CATEGORY_ENABLE, "enable_fluid_container", true);
		propertyTankItemEnabled.setComment("Enable Fluid Container? DEFAULT=true");
		tankItemEnabled = propertyTankItemEnabled.getBoolean();
		propertyTankItemEnabled.set(tankItemEnabled);
		orderEnable.add(propertyTankItemEnabled.getName());
		
		Property propertyTankBlockEnabled = config.get(CATEGORY_ENABLE, "enable_tank_frame", true);
		propertyTankBlockEnabled.setComment("Enable Tank Frame? DEFAULT=true, REQUIRE enable_fluid_container to be true");
		tankBlockEnabled = propertyTankBlockEnabled.getBoolean() && tankItemEnabled;
		propertyTankBlockEnabled.set(tankBlockEnabled);
		orderEnable.add(propertyTankBlockEnabled.getName());
	}

}
