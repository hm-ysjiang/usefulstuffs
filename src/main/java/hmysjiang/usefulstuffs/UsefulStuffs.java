package hmysjiang.usefulstuffs;

import hmysjiang.usefulstuffs.handlers.*;
import hmysjiang.usefulstuffs.init.*;
import hmysjiang.usefulstuffs.proxy.CommonProxy;
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
		AchievementHandler.register();
		
		ModItems.init();
		ModItems.register();
		
		proxy.registerRenders();
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		proxy.registerModelBakeryVariants();
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		
	}
	
}
