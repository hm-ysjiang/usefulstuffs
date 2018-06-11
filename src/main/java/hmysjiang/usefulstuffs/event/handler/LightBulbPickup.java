package hmysjiang.usefulstuffs.event.handler;

import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.items.ItemLightShooter;
import hmysjiang.usefulstuffs.miscs.helper.InventoryHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LightBulbPickup {
	
	@SubscribeEvent
	public static void onPlayerPickUpItem(EntityItemPickupEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		EntityItem item = event.getItem();
		ItemStack stack = item.getEntityItem();
		if (stack.isItemEqual(new ItemStack(ModBlocks.lightbulb))) {
			ItemStack collector = InventoryHelper.findItemStackInPlayerInventory(player, ModItems.lightshooter_c);
			if (collector != null) {
				NBTTagCompound nbtcompound = collector.getTagCompound();
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
	
}
