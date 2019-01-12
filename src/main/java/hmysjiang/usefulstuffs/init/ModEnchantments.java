package hmysjiang.usefulstuffs.init;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.enchantment.EnchantmentFastDraw;
import hmysjiang.usefulstuffs.enchantment.EnchantmentMoonLight;
import hmysjiang.usefulstuffs.enchantment.EnchantmentXL;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ModEnchantments {
	
	@SubscribeEvent
	public static void OnEnchantmentRegister(RegistryEvent.Register<Enchantment> event) {
		if (ConfigManager.glueEnabled)
			event.getRegistry().register(EnchantmentXL.INSTANCE);
		if (ConfigManager.lightBowEnabled || ConfigManager.lightBatteryEnabled)
			event.getRegistry().register(EnchantmentMoonLight.INSTANCE);
		event.getRegistry().register(EnchantmentFastDraw.INSTANCE);
	}
	
}
