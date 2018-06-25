package hmysjiang.usefulstuffs.init;

import hmysjiang.usefulstuffs.potion.PotionLily;
import hmysjiang.usefulstuffs.potion.potiontype.PotionTypeLily;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModPotion {
	
	public static void register() {
		GameRegistry.register(PotionLily.instance);
		GameRegistry.register(PotionTypeLily.instance);
		GameRegistry.register(PotionTypeLily.instance_long);
	}
	
}
