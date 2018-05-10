package hmysjiang.usefulstuffs;

import hmysjiang.usefulstuffs.entitys.projectiles.EntityLightBulb;
import hmysjiang.usefulstuffs.handlers.*;
import hmysjiang.usefulstuffs.init.*;
import hmysjiang.usefulstuffs.items.ItemLightShooterCollector;
import hmysjiang.usefulstuffs.miscs.DmgSrcExcalibur;
import hmysjiang.usefulstuffs.proxy.CommonProxy;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_MCVERSION)
public class UsefulStuffs {
	
	@Instance
	public static UsefulStuffs instance;
	
	@SidedProxy(clientSide = "hmysjiang.usefulstuffs.proxy.ClientProxy", serverSide = "hmysjiang.usefulstuffs.proxy.ServerProxy")
	public static CommonProxy proxy;
	
	
	public static final DmgSrcExcalibur dmgsrcExcalibur = new DmgSrcExcalibur();
	
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		AchievementHandler.register();
		
		ModItems.init();
		ModItems.register();
		ModBlocks.init();
		ModBlocks.register();
		ModEntities.register();
		
		
		proxy.registerRenders();
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(hmysjiang.usefulstuffs.handlers.EventHandler.class);
		
		proxy.registerModelBakeryVariants();
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		
	}
	
}
