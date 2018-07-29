package hmysjiang.usefulstuffs;

import hmysjiang.usefulstuffs.proxy.CommonProxy;
import hmysjiang.usefulstuffs.utils.helper.LogHelper;
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
	
	public UsefulStuffs() {
		LogHelper.info("Hello Minecraft!");
	}
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		if (Reference.TEST_MODE) {
			LogHelper.warn("This is a testing version of UsefulStuffs that contains Items and Blocks for testing purposes"
					+ ", which could possibly make a unrecoverable damage to your world.");
			LogHelper.warn("If you see this message and you are not developer, REPORT TO THE AUTHOR."
					+ "Maybe I forgot to turn off the test-mode again \\(. q .)/");
		}
		proxy.preInit();
		proxy.registerRenders();
		proxy.registerTileEntity();
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		proxy.init();
		proxy.registerModelBakeryVariants();
		proxy.registerSideOnlyEvents();
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		
	}
	
}