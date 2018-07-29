package hmysjiang.usefulstuffs.init;

import hmysjiang.usefulstuffs.event.*;
import hmysjiang.usefulstuffs.utils.handler.KeyBindingHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ModEvents {
	
	public static void register() {
		MinecraftForge.EVENT_BUS.register(CampfireCreate.class);
		MinecraftForge.EVENT_BUS.register(PlayerCraftingListener.class);
		MinecraftForge.EVENT_BUS.register(LightBulbPickup.class);
		MinecraftForge.EVENT_BUS.register(KeyBindingHandler.class);
	}
	
}
