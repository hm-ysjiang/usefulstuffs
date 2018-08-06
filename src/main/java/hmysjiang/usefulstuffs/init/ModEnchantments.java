package hmysjiang.usefulstuffs.init;

import hmysjiang.usefulstuffs.enchantment.EnchantmentXL;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ModEnchantments {
	
	@SubscribeEvent
	public static void OnEnchantmentRegister(RegistryEvent.Register<Enchantment> event) {
		event.getRegistry().register(EnchantmentXL.instance);
	}
	
}
