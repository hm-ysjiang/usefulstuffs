package hmysjiang.usefulstuffs.handlers;

import net.minecraft.entity.player.EntityPlayer;

//Currently not used

/***
 * The code below is from romelo333/notenoughwands
 */

public class XPHandler {
	
    public static int getPlayerXP(EntityPlayer player) {
        return (int)(getExperienceForLevel(player.experienceLevel) + (player.experience * player.xpBarCap()));
    }
    
    public static int getExperienceForLevel(int level) {
    	if (level == 0) 
    		return 0; 
    	if (level > 0 && level < 16) 
    		return level * 17;
    	else if (level > 15 && level < 31) 
    		return (int)(1.5 * Math.pow(level, 2) - 29.5 * level + 360);
    	else 
    		return (int)(3.5 * Math.pow(level, 2) - 151.5 * level + 2220);
	}
    
}
