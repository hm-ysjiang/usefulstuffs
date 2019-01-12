package hmysjiang.usefulstuffs.init;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.blocks.bush.BlockBerryBush;
import hmysjiang.usefulstuffs.blocks.bush.EnumBerryColor;
import hmysjiang.usefulstuffs.blocks.campfire.BlockCampfire;
import hmysjiang.usefulstuffs.blocks.fermenter.BlockMilkFermenter;
import hmysjiang.usefulstuffs.blocks.fierylily.BlockFieryLilyPad;
import hmysjiang.usefulstuffs.blocks.filingcabinets.BlockFilingCabinetUnstackable;
import hmysjiang.usefulstuffs.blocks.gluedbox.BlockGluedBox;
import hmysjiang.usefulstuffs.blocks.lightbulb.BlockLightBulb;
import hmysjiang.usefulstuffs.blocks.playerdetector.BlockPlayerDetector;
import hmysjiang.usefulstuffs.blocks.portalmuffler.BlockPortalMuffler;
import hmysjiang.usefulstuffs.blocks.raindetector.BlockRainDetector;
import hmysjiang.usefulstuffs.blocks.tflipflop.BlockTFlipFlop;
import hmysjiang.usefulstuffs.blocks.universaluser.BlockUniversalUser;
import hmysjiang.usefulstuffs.blocks.well.BlockWell;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class ModBlocks {
	
	public static Block campfire;
	public static Block filing_cabinet_unstackable;
	public static Block glued_box;
	public static Block light_bulb;
	public static Block rain_detector;
	public static Block t_flipflop;
	public static Block well;
	public static Block[] berrybushes = new Block[16];
	public static Block portal_muffler;
	public static Block player_detector;
	public static Block fiery_lily;
	public static Block milk_fermenter;
	public static Block universal_user;
	
	public static void init() {
		campfire = new BlockCampfire(ConfigManager.campfireEnabled);
		filing_cabinet_unstackable = new BlockFilingCabinetUnstackable(ConfigManager.filingCabinetUnstackableEnabled);
		glued_box = new BlockGluedBox(ConfigManager.glueEnabled);
		light_bulb = new BlockLightBulb(ConfigManager.lightBulbEnabled);
		rain_detector = new BlockRainDetector(ConfigManager.rainDetectorEnabled);
		t_flipflop = new BlockTFlipFlop(ConfigManager.tFlipFlopEnabled);
		well = new BlockWell(ConfigManager.wellEnabled);
		for (int i = 0 ; i<EnumBerryColor.values().length ; i++)
			berrybushes[i] = new BlockBerryBush(EnumBerryColor.byMetadata(i), ConfigManager.berryEnabled);
		portal_muffler = new BlockPortalMuffler(ConfigManager.portalMufflerEnabled);
		player_detector = new BlockPlayerDetector(ConfigManager.playerDetectorEnabled);
		fiery_lily = new BlockFieryLilyPad(ConfigManager.fieryLilyEnabled);
		milk_fermenter = new BlockMilkFermenter(ConfigManager.milkFermenterEnabled);
		universal_user = new BlockUniversalUser(ConfigManager.universalUserEnabled);
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		if (ConfigManager.campfireEnabled) {
			registry.register(campfire);
		}
		if (ConfigManager.filingCabinetUnstackableEnabled) {
			registry.register(filing_cabinet_unstackable);
		}
		if (ConfigManager.glueEnabled) {
			registry.register(glued_box);
		}
		if (ConfigManager.lightBulbEnabled) {
			registry.register(light_bulb);
		}
		if (ConfigManager.rainDetectorEnabled) {
			registry.register(rain_detector);
		}
		if (ConfigManager.tFlipFlopEnabled) {
			registry.register(t_flipflop);
		}
		if (ConfigManager.wellEnabled) {
			registry.register(well);
		}
		if (ConfigManager.portalMufflerEnabled) {
			registry.register(portal_muffler);
		}
		if (ConfigManager.playerDetectorEnabled) {
			registry.register(player_detector);
		}
		if (ConfigManager.fieryLilyEnabled) {
			registry.register(fiery_lily);
		}
		if (ConfigManager.milkFermenterEnabled) {
			registry.register(milk_fermenter);
		}
		if (ConfigManager.universalUserEnabled) {
			registry.register(universal_user);
		}
		if (ConfigManager.berryEnabled) {
			register(registry, berrybushes);
		}
	}
	
	@SubscribeEvent
	public static void onModelRegistry(ModelRegistryEvent event) {
		UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(campfire));
		UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(filing_cabinet_unstackable));
		UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(glued_box));
		UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(light_bulb));
		UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(rain_detector));
		UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(t_flipflop));
		UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(well));
		UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(portal_muffler));
		UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(player_detector));
		UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(fiery_lily));
		UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(milk_fermenter));
		UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(universal_user));
		for (Block block: berrybushes)
			UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(block));
	}
	
	@SideOnly(Side.CLIENT)
	public static void createStateMapper() {
		ModelLoader.setCustomStateMapper(player_detector, (new StateMap.Builder().ignore(BlockPlayerDetector.POWERED)).build());
	}
	
	private static void register(IForgeRegistry<Block> registry, Block... blocks) {
		registry.registerAll(blocks);
		for (Block block: blocks)
			block.setCreativeTab(UsefulStuffs.TAB);
	}
	
}
