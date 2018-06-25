package hmysjiang.usefulstuffs.init;

import hmysjiang.usefulstuffs.event.handler.*;
import net.minecraftforge.common.MinecraftForge;

public class ModEvents {
	
	public static void register() {
		MinecraftForge.EVENT_BUS.register(CampfireCreate.class);
		MinecraftForge.EVENT_BUS.register(PlayerCraftingListener.class);
		MinecraftForge.EVENT_BUS.register(LightBulbPickup.class);
		MinecraftForge.EVENT_BUS.register(SpriteHandler.instance);
	}
	
}
