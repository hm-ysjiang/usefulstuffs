package hmysjiang.usefulstuffs.miscs.handler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class AchievementHandler {
	
	private static List<Achievement> achievements = new ArrayList<Achievement>();
	
//	public static Achievement achievementExcalibur = createAchievement("excalibur", 0, 0, ModItems.excalibur);
	public static Achievement achievementLightBulb = createAchievement("light_bulb", 0, 0, ModBlocks.lightbulb, null);
	public static Achievement achievementLightShooter = createAchievement("light_shooter", 0, 1, ModItems.lightshooter, achievementLightBulb);
	public static Achievement achievementRainDetector = createAchievement("rain_detector", 1, 0, ModBlocks.rain_detector, null);
	
	public static void register() {
		Achievement[] achievementArray = new Achievement[achievements.size()];
		for (Achievement achievement: achievements) {
			achievement.registerStat();
			achievementArray[achievements.indexOf(achievement)] = achievement;
		}
		AchievementPage.registerAchievementPage(new AchievementPage(Reference.NAME, achievementArray));
	}
	
	private static Achievement createAchievement(String name, int row, int column, Item icon, @Nullable Achievement parent) {
		Achievement achievement = new Achievement("usefulstuffs.achievement." + name, name, column, row, icon, parent);
		achievements.add(achievement);
		return achievement;
	}
	
	private static Achievement createAchievement(String name, int row, int column, Block icon, @Nullable Achievement parent) {
		return createAchievement(name, row, column, Item.getItemFromBlock(icon), parent);
	}
	
	private static Achievement createAchievement(String name, int row, int column, ItemStack icon, @Nullable Achievement parent) {
		Achievement achievement = new Achievement("usefulstuffs.achievement." + name, name, column, row, icon, parent);
		achievements.add(achievement);
		return achievement;
	}
	
}
