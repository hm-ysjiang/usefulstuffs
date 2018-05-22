package hmysjiang.usefulstuffs.miscs.handler;

import java.util.ArrayList;
import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class AchievementHandler {
	
	private static List<Achievement> achievements = new ArrayList<Achievement>();
	
//	public static Achievement achievementExcalibur = createAchievement("excalibur", 0, 0, ModItems.excalibur);
	
	public static void register() {
		Achievement[] achievementArray = new Achievement[achievements.size()];
		for (Achievement achievement: achievements) {
			achievement.registerStat();
			achievementArray[achievements.indexOf(achievement)] = achievement;
		}
		AchievementPage.registerAchievementPage(new AchievementPage(Reference.NAME, achievementArray));
	}
	
	private static Achievement createAchievement(String name, int column, int row, Item icon) {
		Achievement achievement = new Achievement("achievement." + name, name, column, row, icon, (Achievement)null);
		achievements.add(achievement);
		return achievement;
	}
	
	private static Achievement createAchievement(String name, int column, int row, Block icon) {
		Achievement achievement = new Achievement("achievement." + name, name, column, row, icon, (Achievement)null);
		achievements.add(achievement);
		return achievement;
	}
	
	private static Achievement createAchievement(String name, int column, int row, ItemStack icon) {
		Achievement achievement = new Achievement("achievement." + name, name, column, row, icon, (Achievement)null);
		achievements.add(achievement);
		return achievement;
	}
	
}
