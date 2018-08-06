package hmysjiang.usefulstuffs.proxy;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.blocks.campfire.TileEntityCampfire;
import hmysjiang.usefulstuffs.blocks.filingcabinet.TileEntityFilingCabinet;
import hmysjiang.usefulstuffs.blocks.gluedbox.TileEntityGluedBox;
import hmysjiang.usefulstuffs.blocks.raindetector.TileEntityRainDetector;
import hmysjiang.usefulstuffs.blocks.tflipflop.TileEntityTFlipFlop;
import hmysjiang.usefulstuffs.blocks.well.TileEntityWell;
import hmysjiang.usefulstuffs.client.gui.GuiHandler;
import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModEntities;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.init.ModRecipe;
import hmysjiang.usefulstuffs.network.PacketHandler;
import hmysjiang.usefulstuffs.utils.OreDictHelper;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public void preInit() {
		ModBlocks.init();
		ModItems.init();
		ModRecipe.init();
		
		ModEntities.register();
		PacketHandler.init();
		
		registerTileEntity();
	}
	
	public void init() {
		NetworkRegistry.INSTANCE.registerGuiHandler(UsefulStuffs.instance, new GuiHandler());
		OreDictHelper.pullOreRegistries();
	}
	
	public void postInit() {
		
	}
	
	public void registerItemRenders(Item item) {}
	
	private void registerTileEntity() {
		GameRegistry.registerTileEntity(TileEntityCampfire.class, new ResourceLocation(Reference.MOD_ID, "campfire"));
		GameRegistry.registerTileEntity(TileEntityFilingCabinet.class, new ResourceLocation(Reference.MOD_ID, "filing_cabinet"));
		GameRegistry.registerTileEntity(TileEntityGluedBox.class, new ResourceLocation(Reference.MOD_ID, "glued_box"));
		GameRegistry.registerTileEntity(TileEntityRainDetector.class, new ResourceLocation(Reference.MOD_ID, "rain_detector"));
		GameRegistry.registerTileEntity(TileEntityTFlipFlop.class, new ResourceLocation(Reference.MOD_ID, "t_flipflop"));
		GameRegistry.registerTileEntity(TileEntityWell.class, new ResourceLocation(Reference.MOD_ID, "well"));
	}
	
}
