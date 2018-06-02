package hmysjiang.usefulstuffs.event.handler;

import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.miscs.handler.AchievementHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class PlayerCraftingListener {
	
	@SubscribeEvent
	public static void onPlayerCraft(PlayerEvent.ItemCraftedEvent event) {
		if (event.player != null && event.crafting != null) {
			EntityPlayer player = event.player;
			ItemStack crafting = event.crafting;
			if (!player.hasAchievement(AchievementHandler.achievementLightBulb) && crafting.isItemEqual(new ItemStack(ModBlocks.lightbulb))) {
				player.addStat(AchievementHandler.achievementLightBulb);
			}
			else if (!player.hasAchievement(AchievementHandler.achievementLightShooter) && crafting.isItemEqual(new ItemStack(ModItems.lightshooter))) {
				player.addStat(AchievementHandler.achievementLightShooter);
			}
			else if (!player.hasAchievement(AchievementHandler.achievementRainDetector) && crafting.isItemEqual(new ItemStack(ModBlocks.rain_detector))) {
				player.addStat(AchievementHandler.achievementRainDetector);
			}
		}
	}
	
}
