package hmysjiang.usefulstuffs.plugins.jei;

import java.util.ArrayList;
import java.util.List;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.items.ItemTankContainer;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemJeiDescription {
	private static List<ItemStack> berries = new ArrayList<ItemStack>();
	private static List<ItemStack> tankItems = new ArrayList<ItemStack>();
	
	static {
		for (int i = 0 ; i<16 ; i++)
			berries.add(new ItemStack(ModItems.berry, 1, i));
		for (ItemTankContainer.TankTier tier: ItemTankContainer.TankTier.values())
			if (tier.availiable())
				tankItems.add(new ItemStack(ModItems.tank_container, 1, tier.getMeta()));
	}
	
	public static void registerDescription(IModRegistry registry) {
		registry.addIngredientInfo(new ItemStack(ModItems.water_blacklist), VanillaTypes.ITEM, "usefulstuffs.waterfilter.jei_1", 
																							   "usefulstuffs.waterfilter.jei_2");
		if (ConfigManager.shooterAcceptBattery) {
			registry.addIngredientInfo(new ItemStack(ModItems.light_shooter), VanillaTypes.ITEM, "usefulstuffs.light_shooter.jei_1",
																								 "usefulstuffs.light_shooter.jei_2");
			registry.addIngredientInfo(new ItemStack(ModItems.light_shooter_collecter), VanillaTypes.ITEM, "usefulstuffs.light_shooter.jei_1",
																								 		   "usefulstuffs.light_shooter.jei_2",
																								 		   "usefulstuffs.light_shooter.jei_c");
		}
		else {
			registry.addIngredientInfo(new ItemStack(ModItems.light_shooter), VanillaTypes.ITEM, "usefulstuffs.light_shooter.jei_1");
			registry.addIngredientInfo(new ItemStack(ModItems.light_shooter_collecter), VanillaTypes.ITEM, "usefulstuffs.light_shooter.jei_1",
																										   "usefulstuffs.light_shooter.jei_c");
		}
		registry.addIngredientInfo(new ItemStack(ModItems.packing_glue), VanillaTypes.ITEM, "usefulstuffs.packing_glue.jei_1");
		registry.addIngredientInfo(new ItemStack(ModItems.packing_glue), VanillaTypes.ITEM, "usefulstuffs.packing_glue.jei_2");
		registry.addIngredientInfo(new ItemStack(ModItems.umbrella), VanillaTypes.ITEM, "usefulstuffs.umbrella.jei");
		registry.addIngredientInfo(new ItemStack(ModItems.belt_lily), VanillaTypes.ITEM, "usefulstuffs.belt_lily.jei");
		registry.addIngredientInfo(new ItemStack(ModItems.bag_storage), VanillaTypes.ITEM, "usefulstuffs.bag_storage.jei");
		registry.addIngredientInfo(new ItemStack(ModItems.belt_fiery_lily), VanillaTypes.ITEM, "usefulstuffs.belt_fiery_lily.jei");
		registry.addIngredientInfo(new ItemStack(ModItems.backpack), VanillaTypes.ITEM, "usefulstuffs.backpack.jei");
		registry.addIngredientInfo(new ItemStack(ModItems.mining_backpack), VanillaTypes.ITEM, "usefulstuffs.mining_backpack.jei_1");
		registry.addIngredientInfo(new ItemStack(ModItems.mining_backpack), VanillaTypes.ITEM, "usefulstuffs.mining_backpack.jei_2");
		registry.addIngredientInfo(new ItemStack(ModItems.cheese), VanillaTypes.ITEM, "usefulstuffs.cheese.jei");
		if (ConfigManager.enableCampfireCraft) {
			if (ConfigManager.campfireNeedsFuel) {
				registry.addIngredientInfo(new ItemStack(ModBlocks.campfire), VanillaTypes.ITEM, "usefulstuffs.campfire.jei_c",
																								 "usefulstuffs.campfire.jei",
																								 "usefulstuffs.campfire.jei_n");
			}
			else {
				registry.addIngredientInfo(new ItemStack(ModBlocks.campfire), VanillaTypes.ITEM, "usefulstuffs.campfire.jei_c",
																								 "usefulstuffs.campfire.jei");
			}
		}
		else {
			if (ConfigManager.campfireNeedsFuel) {
				registry.addIngredientInfo(new ItemStack(ModBlocks.campfire), VanillaTypes.ITEM, "usefulstuffs.campfire.jei",
						 																		 "usefulstuffs.campfire.jei_n",
																								 "usefulstuffs.campfire.jei_u");
			}
			else {
				registry.addIngredientInfo(new ItemStack(ModBlocks.campfire), VanillaTypes.ITEM, "usefulstuffs.campfire.jei",
																								 "usefulstuffs.campfire.jei_u");
			}
		}
		registry.addIngredientInfo(new ItemStack(ModBlocks.filing_cabinet_unstackable), VanillaTypes.ITEM, "usefulstuffs.filing_cabinet.jei");
		registry.addIngredientInfo(new ItemStack(ModBlocks.filing_cabinet_nbt), VanillaTypes.ITEM, "usefulstuffs.filing_cabinet2.jei");
		registry.addIngredientInfo(new ItemStack(ModBlocks.well), VanillaTypes.ITEM, "usefulstuffs.well.jei");
		registry.addIngredientInfo(new ItemStack(ModBlocks.portal_muffler), VanillaTypes.ITEM, "usefulstuffs.portal_muffler.jei");
		if (ConfigManager.onlyDetectOneSide) 
			registry.addIngredientInfo(new ItemStack(ModBlocks.player_detector), VanillaTypes.ITEM, "usefulstuffs.player_detector.jei_f");
		else 
			registry.addIngredientInfo(new ItemStack(ModBlocks.player_detector), VanillaTypes.ITEM, "usefulstuffs.player_detector.jei");
		registry.addIngredientInfo(new ItemStack(ModBlocks.fiery_lily), VanillaTypes.ITEM, "usefulstuffs.fiery_lily.jei");
//		registry.addIngredientInfo(new ItemStack(ModBlocks.milk_fermenter), VanillaTypes.ITEM, "usefulstuffs.milk_fermenter.jei");
		registry.addIngredientInfo(new ItemStack(ModBlocks.universal_user), VanillaTypes.ITEM, "usefulstuffs.universal_user.jei");
		registry.addIngredientInfo(berries, VanillaTypes.ITEM, "usefulstuffs.berry.jei");
		registry.addIngredientInfo(new ItemStack(ModItems.milk_bag), VanillaTypes.ITEM, "usefulstuffs.milk_bag.jei");
		registry.addIngredientInfo(new ItemStack(ModItems.light_bow), VanillaTypes.ITEM, "usefulstuffs.light_bow.jei");
		registry.addIngredientInfo(new ItemStack(ModItems.light_battery), VanillaTypes.ITEM, "usefulstuffs.light_battery.jei");
		if (ConfigManager.chickenDropsFeather) {
			registry.addIngredientInfo(new ItemStack(Items.FEATHER), VanillaTypes.ITEM, "usefulstuffs.feather.jei_1",
																						"usefulstuffs.feather.jei_2");
		}
		registry.addIngredientInfo(new ItemStack(ModItems.charm_potato), VanillaTypes.ITEM, "usefulstuffs.potato_charm.jei_1",
																							"usefulstuffs.potato_charm.jei_2");
		registry.addIngredientInfo(new ItemStack(ModItems.ring_stablizing), VanillaTypes.ITEM, "usefulstuffs.stabilizing_ring.jei");
		registry.addIngredientInfo(tankItems, VanillaTypes.ITEM, "usefulstuffs.tank_container.jei", 
																 "usefulstuffs.tank_formation.jei");
		registry.addIngredientInfo(new ItemStack(ModBlocks.tank), VanillaTypes.ITEM, "usefulstuffs.tank_block.jei", 
																					 "usefulstuffs.tank_formation.jei");
		registry.addIngredientInfo(new ItemStack(ModItems.fortress_finder), VanillaTypes.ITEM, "usefulstuffs.fortress_finder.jei_1", 
																							   "usefulstuffs.fortress_finder.jei_2");
	}
	
}
