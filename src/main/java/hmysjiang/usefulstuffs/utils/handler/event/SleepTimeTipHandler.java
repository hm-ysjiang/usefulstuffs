package hmysjiang.usefulstuffs.utils.handler.event;

import java.util.ArrayList;
import java.util.List;

import hmysjiang.usefulstuffs.network.PacketHandler;
import hmysjiang.usefulstuffs.network.packet.PlaySound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class SleepTimeTipHandler {
	
	public static final SleepTimeTipHandler INSTANCE = new SleepTimeTipHandler();
	private List<EntityPlayer> player2Tip = new ArrayList<>();
	private List<EntityPlayer> player2Confirm = new ArrayList<>();
	private int partialSec = 0;
	
	
	@SubscribeEvent
	public void onTick(TickEvent.ServerTickEvent event) {
		if (event.phase == Phase.END) {
			partialSec++;
			partialSec %= 20;
			if (partialSec == 0) {
				if (player2Tip.size() > 0) {
					if (!player2Tip.get(0).world.isDaytime()) {
						player2Tip.clear();
						player2Confirm.clear();
					}
				}
				for (EntityPlayer player: player2Tip) {
					player.sendStatusMessage(new TextComponentTranslation("usefulstuffs.bedstatus", ((12540 - player.world.getWorldTime() % 24000) / 20)), true);
				}
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.LOW)
	public void trySleep(PlayerSleepInBedEvent event) {
		if (event.getEntityPlayer() == null || event.getEntityPlayer().world.isRemote)	return;
		EntityPlayer player = event.getEntityPlayer();
		if (player.world.isDaytime()) {
			if (!player2Confirm.contains(player) && !player2Tip.contains(player)) {
				player2Confirm.add(player);
			}
			else if (player2Confirm.contains(player)) {
				player2Confirm.remove(player);
				player2Tip.add(player);
				PacketHandler.INSTANCE.sendTo(new PlaySound(player.getEntityId(), event.getPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP.getRegistryName(), SoundCategory.PLAYERS, 1.0F, 1.0F), (EntityPlayerMP) player);
			}
			else if (player2Tip.contains(player)) {
				player2Tip.remove(player);
			}
		}
		else {
			if (player2Confirm.contains(player))
				player2Confirm.remove(player);
			if (player2Tip.contains(player))
				player2Tip.remove(player);
		}
	}
	
	@SubscribeEvent
	public void onPlayerWake(PlayerWakeUpEvent event) {
		if (event.getEntityPlayer() == null || event.getEntityPlayer().world.isRemote)	return;
		EntityPlayer player = event.getEntityPlayer();
		if (player2Confirm.contains(player))
			player2Confirm.remove(player);
		if (player2Tip.contains(player))
			player2Tip.remove(player);
	}
	
}
