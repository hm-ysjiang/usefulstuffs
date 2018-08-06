package hmysjiang.usefulstuffs.init;

import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.blocks.campfire.BlockCampfire;
import hmysjiang.usefulstuffs.blocks.filingcabinet.BlockFilingCabinet;
import hmysjiang.usefulstuffs.blocks.gluedbox.BlockGluedBox;
import hmysjiang.usefulstuffs.blocks.lightbulb.BlockLightBulb;
import hmysjiang.usefulstuffs.blocks.raindetector.BlockRainDetector;
import hmysjiang.usefulstuffs.blocks.tflipflop.BlockTFlipFlop;
import hmysjiang.usefulstuffs.blocks.well.BlockWell;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ModBlocks {
	
	public static Block campfire;
	public static Block filing_cabinet;
	public static Block glued_box;
	public static Block light_bulb;
	public static Block rain_detector;
	public static Block t_flipflop;
	public static Block well;
	
	public static void init() {
		campfire = new BlockCampfire();
		filing_cabinet = new BlockFilingCabinet();
		glued_box = new BlockGluedBox();
		light_bulb = new BlockLightBulb();
		rain_detector = new BlockRainDetector();
		t_flipflop = new BlockTFlipFlop();
		well = new BlockWell();
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(campfire);
		event.getRegistry().register(filing_cabinet);
		event.getRegistry().register(glued_box);
		event.getRegistry().register(light_bulb);
		event.getRegistry().register(rain_detector);
		event.getRegistry().register(t_flipflop);
		event.getRegistry().register(well);
	}
	
	@SubscribeEvent
	public static void onModelRegistry(ModelRegistryEvent event) {
		UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(campfire));
		UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(filing_cabinet));
		UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(glued_box));
		UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(light_bulb));
		UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(rain_detector));
		UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(t_flipflop));
		UsefulStuffs.proxy.registerItemRenders(Item.getItemFromBlock(well));
	}
	
}
