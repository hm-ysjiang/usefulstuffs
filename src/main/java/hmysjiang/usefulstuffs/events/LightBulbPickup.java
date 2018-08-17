package hmysjiang.usefulstuffs.events;

import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.items.ItemLightShooter;
import hmysjiang.usefulstuffs.utils.InventoryHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.ItemStackHandler;

@EventBusSubscriber
public class LightBulbPickup {
	
	@SubscribeEvent
	public static void onPlayerPickUpItem(EntityItemPickupEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		ItemStack stack = event.getItem().getItem();
		if (stack.isItemEqual(new ItemStack(ModBlocks.light_bulb))) {
			ItemStack collector = InventoryHelper.findStackInPlayerInventory(player, new ItemStack(ModItems.light_shooter_collecter));
			if (!collector.isEmpty()) {
				stack.setCount(ItemLightShooter.incrAmmoCount(collector, stack.getCount()));
			}
		}
	}
	
}
