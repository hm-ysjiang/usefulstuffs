package hmysjiang.usefulstuffs.events;

import hmysjiang.usefulstuffs.init.ModBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class CampfireCreate {
	
	@SubscribeEvent
	public static void onInteract(PlayerInteractEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		if (player.isSneaking() && player.getHeldItemMainhand() != null && player.getHeldItemMainhand().isItemEqualIgnoreDurability(new ItemStack(Items.FLINT_AND_STEEL))) {
			if (player.world.getBlockState(event.getPos()) == Blocks.LOG.getDefaultState()) {
				if (!player.capabilities.isCreativeMode) {
					ItemStack stack = player.getHeldItemMainhand();
					if (stack.getItemDamage() >= stack.getMaxDamage()) {
						for (int i = 0 ; i<player.inventory.getSizeInventory() ; i++) {
							if (player.inventory.getStackInSlot(i) == stack) {
								player.inventory.removeStackFromSlot(i);
							}
						}
					}
					else { 
						stack.setItemDamage(stack.getItemDamage()+1);
					}
				}
				player.world.setBlockState(event.getPos(), ModBlocks.campfire.getDefaultState());
				event.setCanceled(true);
			}
		}
	}
	
}
