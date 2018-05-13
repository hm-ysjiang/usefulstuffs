package hmysjiang.usefulstuffs.init;

import net.minecraftforge.common.MinecraftForge;

public class ModEvents {

	public static void register() {
		register(hmysjiang.usefulstuffs.handlers.EventHandler.class);
	}
	
	private static void register(Object target) {
		MinecraftForge.EVENT_BUS.register(target);
	}
	
}