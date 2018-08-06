package hmysjiang.usefulstuffs.events;

import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.items.ItemLightShooter;
import hmysjiang.usefulstuffs.utils.InventoryHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class LightBulbPickup {
	
	@SubscribeEvent
	public static void onPlayerPickUpItem(EntityItemPickupEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		ItemStack stack = event.getItem().getItem();
		if (stack.isItemEqual(new ItemStack(ModBlocks.light_bulb))) {
			ItemStack collector = InventoryHelper.findStackInPlayerInventory(player, new ItemStack(ModItems.light_shooter_collecter));
			if (!collector.isEmpty()) {
				NBTTagCompound nbtcompound = collector.getTagCompound();
				int spare = ItemLightShooter.getMaxAmmo() - nbtcompound.getInteger("Ammo");
				if (spare == 0)
					return;
				if (stack.getCount() > spare) {
					stack.setCount(stack.getCount() - spare);
					nbtcompound.setInteger("Ammo", ItemLightShooter.getMaxAmmo());
				}
				else {
					nbtcompound.setInteger("Ammo", nbtcompound.getInteger("Ammo") + stack.getCount());
					stack.setCount(0);
				}
			}
		}
	}
	
}
