package hmysjiang.usefulstuffs.proxy;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.blocks.campfire.TileEntityCampfire;
import hmysjiang.usefulstuffs.blocks.fermenter.TileEntityMilkFermenter;
import hmysjiang.usefulstuffs.blocks.filingcabinets.TileEntityFilingCabinetNBT;
import hmysjiang.usefulstuffs.blocks.filingcabinets.TileEntityFilingCabinetUnstackable;
import hmysjiang.usefulstuffs.blocks.gluedbox.TileEntityGluedBox;
import hmysjiang.usefulstuffs.blocks.playerdetector.TileEntityPlayerDetector;
import hmysjiang.usefulstuffs.blocks.portalmuffler.TileEntityPortalMuffler;
import hmysjiang.usefulstuffs.blocks.raindetector.TileEntityRainDetector;
import hmysjiang.usefulstuffs.blocks.tank.TileEntityTankFrame;
import hmysjiang.usefulstuffs.blocks.universaluser.TileEntityUniversalUser;
import hmysjiang.usefulstuffs.blocks.well.TileEntityWell;
import hmysjiang.usefulstuffs.client.gui.GuiHandler;
import hmysjiang.usefulstuffs.enchantment.EnchantmentFastDraw;
import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModEntities;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.init.ModRecipe;
import hmysjiang.usefulstuffs.network.PacketHandler;
import hmysjiang.usefulstuffs.utils.fakeplayer.FakePlayerHandler;
import hmysjiang.usefulstuffs.utils.handler.event.SleepTimeTipHandler;
import hmysjiang.usefulstuffs.utils.helper.OreDictHelper;
import hmysjiang.usefulstuffs.world.gen.BerryBushGenerator;
import hmysjiang.usefulstuffs.world.gen.FieryLilyGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public void preInit() {
		ModBlocks.init();
		ModItems.init();
		
		ModEntities.register();
		PacketHandler.init();
		
		registerTileEntity();
		
		MinecraftForge.EVENT_BUS.register(EnchantmentFastDraw.INSTANCE);
		MinecraftForge.EVENT_BUS.register(FakePlayerHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(SleepTimeTipHandler.INSTANCE);
	}
	
	public void init() {
		NetworkRegistry.INSTANCE.registerGuiHandler(UsefulStuffs.instance, new GuiHandler());
		OreDictHelper.init();
		if (ConfigManager.berryEnabled)
			GameRegistry.registerWorldGenerator(new BerryBushGenerator(), 0);
		if (ConfigManager.fieryLilyEnabled)
			GameRegistry.registerWorldGenerator(new FieryLilyGenerator(), 0);
		
		registerItemVariants();
	}
	
	public void postInit() {
		
	}
	
	public void registerItemRenders(Item item) {}
	
	public void registerItemRenders(Item item, int meta, String location) {}
	
	public void registerItemVariants() {}
	
	private void registerTileEntity() {
		if (ConfigManager.campfireEnabled)
			GameRegistry.registerTileEntity(TileEntityCampfire.class, new ResourceLocation(Reference.MOD_ID, "campfire"));
		if (ConfigManager.filingCabinetUnstackableEnabled)
			GameRegistry.registerTileEntity(TileEntityFilingCabinetUnstackable.class, new ResourceLocation(Reference.MOD_ID, "filing_cabinet"));
		if (ConfigManager.glueEnabled)
			GameRegistry.registerTileEntity(TileEntityGluedBox.class, new ResourceLocation(Reference.MOD_ID, "glued_box"));
		if (ConfigManager.rainDetectorEnabled)
			GameRegistry.registerTileEntity(TileEntityRainDetector.class, new ResourceLocation(Reference.MOD_ID, "rain_detector"));
		if (ConfigManager.wellEnabled)
			GameRegistry.registerTileEntity(TileEntityWell.class, new ResourceLocation(Reference.MOD_ID, "well"));
		if (ConfigManager.portalMufflerEnabled)
			GameRegistry.registerTileEntity(TileEntityPortalMuffler.class, new ResourceLocation(Reference.MOD_ID, "portal_muffler"));
		if (ConfigManager.playerDetectorEnabled)
			GameRegistry.registerTileEntity(TileEntityPlayerDetector.class, new ResourceLocation(Reference.MOD_ID, "player_detector"));
		if (ConfigManager.milkBagEnabled)
			GameRegistry.registerTileEntity(TileEntityMilkFermenter.class, new ResourceLocation(Reference.MOD_ID, "milk_fermenter"));
		if (ConfigManager.universalUserEnabled)
			GameRegistry.registerTileEntity(TileEntityUniversalUser.class, new ResourceLocation(Reference.MOD_ID, "universal_user"));
		if (ConfigManager.filingCabinetNbtEnabled)
			GameRegistry.registerTileEntity(TileEntityFilingCabinetNBT.class, new ResourceLocation(Reference.MOD_ID, "filing_cabinet2"));
		if (ConfigManager.tankItemEnabled)
			GameRegistry.registerTileEntity(TileEntityTankFrame.class, new ResourceLocation(Reference.MOD_ID, "tank"));
	}
	
}
