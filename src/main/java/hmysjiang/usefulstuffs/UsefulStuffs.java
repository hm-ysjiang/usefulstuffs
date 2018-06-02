package hmysjiang.usefulstuffs;

import hmysjiang.usefulstuffs.init.*;
import hmysjiang.usefulstuffs.miscs.handler.AchievementHandler;
import hmysjiang.usefulstuffs.network.PacketHandler;
import hmysjiang.usefulstuffs.proxy.CommonProxy;
import hmysjiang.usefulstuffs.recipe.ModCraftingRecipes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_MCVERSION)
public class UsefulStuffs {
	
	@Instance
	public static UsefulStuffs instance;
	
	@SidedProxy(clientSide = "hmysjiang.usefulstuffs.proxy.ClientProxy", serverSide = "hmysjiang.usefulstuffs.proxy.ServerProxy")
	public static CommonProxy proxy;
	
	
	
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		ModItems.init();
		ModItems.register();
		ModBlocks.init();
		ModBlocks.register();
		ModEntities.register();
		
		proxy.registerRenders();
		proxy.registerTileEntity();
		
		PacketHandler.init();
		AchievementHandler.register();
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		ModEvents.register();
		ModCraftingRecipes.register();
		
		proxy.registerModelBakeryVariants();
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		
	}
	
}