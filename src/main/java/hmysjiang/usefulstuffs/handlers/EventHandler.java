package hmysjiang.usefulstuffs.handlers;

import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.items.ItemLightShooter;
import hmysjiang.usefulstuffs.items.ItemLightShooterCollector;
import hmysjiang.usefulstuffs.miscs.InventoryCheck;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {
	
	@SubscribeEvent
	public static void onPlayerPickUpItem(EntityItemPickupEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		ItemStack stack = event.getItem().getEntityItem();
		if (!stack.isItemEqual(new ItemStack(Item.getItemFromBlock(ModBlocks.lightbulb))))
			return;
		ItemStack light_shooter_c = InventoryCheck.findItemStackInPlayerMainInventory(player, ModItems.lightshooter_c);
		if (light_shooter_c != null) {
			NBTTagCompound nbtcompound = light_shooter_c.getTagCompound();
			int spare = ItemLightShooter.getMaxAmmo() - nbtcompound.getInteger("Ammo");
			if (spare == 0)
				return;
			if (stack.stackSize > spare) {
				stack.stackSize-=spare;
				nbtcompound.setInteger("Ammo", ItemLightShooter.getMaxAmmo());
			}
			else {
				nbtcompound.setInteger("Ammo", nbtcompound.getInteger("Ammo")+stack.stackSize);
				stack.stackSize = 0;
			}
		}
	}

}
