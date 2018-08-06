package hmysjiang.usefulstuffs.utils;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictHelper {
	
	public static List<ItemStack> slimes;
	
	public static void pullOreRegistries() {
		slimes = OreDictionary.getOres("slimeball");
	}
	
}
