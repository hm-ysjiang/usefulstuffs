package hmysjiang.usefulstuffs.init;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.enchantment.EnchantmentEndest;
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
		if (ConfigManager.enableXl)
			event.getRegistry().register(EnchantmentXL.INSTANCE);
		if (ConfigManager.enableMoonlight) {
			event.getRegistry().register(EnchantmentMoonLight.INSTANCE);
		}
		if (ConfigManager.enableFastdraw) {
			event.getRegistry().register(EnchantmentFastDraw.INSTANCE);
		}
		if (ConfigManager.enableEndest) {
			event.getRegistry().register(EnchantmentEndest.INSTANCE);
		}
	}
	
}
