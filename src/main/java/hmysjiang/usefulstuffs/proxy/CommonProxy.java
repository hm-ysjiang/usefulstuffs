package hmysjiang.usefulstuffs.proxy;

import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.client.gui.GUIHandler;
import hmysjiang.usefulstuffs.enchantment.EnchantmentXL;
import hmysjiang.usefulstuffs.init.*;
import hmysjiang.usefulstuffs.network.PacketHandler;
import hmysjiang.usefulstuffs.recipe.ModCraftingRecipes;
import hmysjiang.usefulstuffs.tileentity.*;
import hmysjiang.usefulstuffs.utils.handler.AchievementHandler;
import hmysjiang.usefulstuffs.utils.handler.KeyBindingHandler;
import hmysjiang.usefulstuffs.utils.helper.OreDictHelper;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public static void preInit() {
		ModItems.init();
		ModItems.register();
		ModBlocks.init();
		ModBlocks.register();
		ModEntities.register();
		PacketHandler.init();
		AchievementHandler.register();
		GameRegistry.register(EnchantmentXL.instance);
	}
	
	public static void init() {
		ModEvents.register();
		ModPotion.register();
		ModCraftingRecipes.register();
		NetworkRegistry.INSTANCE.registerGuiHandler(UsefulStuffs.instance, new GUIHandler());
		KeyBindingHandler.init();
		OreDictHelper.pullOreRegistries();
	}
	
	public static void postInit() {
		
	}
	
	public void registerTileEntity() {
		GameRegistry.registerTileEntity(TileEntityWell.class, "usefulstuffs:well_of_waterfall");
		GameRegistry.registerTileEntity(TileEntityRainDetector.class, "usefulstuffs:rain_detector");
		GameRegistry.registerTileEntity(TileEntityCampfire.class, "usefulstuffs:campfire");
		GameRegistry.registerTileEntity(TileEntityLantern.class, "usefulstuffs:lantern");
		GameRegistry.registerTileEntity(TileEntityFilingCabinet.class, "usefulstuffs:filing_cabinet");
		GameRegistry.registerTileEntity(TileEntityGluedBox.class, "usefulstuffs:glued_box");
		GameRegistry.registerTileEntity(TileEntityTFlipFlop.class, "usefulstuffs:t_flipflop");
	}
	
	public void registerSideOnlyEvents() {}
	
	public void registerRenders() {}
	
	public void registerModelBakeryVariants() {}
	
}