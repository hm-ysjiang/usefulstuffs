package hmysjiang.usefulstuffs.utils.helper;

import java.util.List;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.blocks.bush.EnumBerryColor;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictHelper {
	
	public static List<ItemStack> slimes;
	private static boolean inited = false;
	
	public static void init() {
		if (!inited) {
			pullOreRegistries();
			register();
		}
	}
	
	private static void pullOreRegistries() {
		slimes = OreDictionary.getOres("slimeball");
	}
	
	private static void register() {
		//berries
		LogHelper.info("Registering ItemBerry-s to OreDictionary");
		if (ConfigManager.berryEnabled) {
			for (EnumBerryColor color: EnumBerryColor.values()) {
				OreDictionary.registerOre("dye", new ItemStack(ModItems.berry, 1, color.getMetadata()));
				OreDictionary.registerOre("listAllberry", new ItemStack(ModItems.berry, 1, color.getMetadata()));
				OreDictionary.registerOre("listAllfruit", new ItemStack(ModItems.berry, 1, color.getMetadata()));	
			}
			OreDictionary.registerOre("dyeWhite", new ItemStack(ModItems.berry, 1, 0));
			OreDictionary.registerOre("dyeOrange", new ItemStack(ModItems.berry, 1, 1));
			OreDictionary.registerOre("dyeMagenta", new ItemStack(ModItems.berry, 1, 2));
			OreDictionary.registerOre("dyeLightBlue", new ItemStack(ModItems.berry, 1, 3));
			OreDictionary.registerOre("dyeYellow", new ItemStack(ModItems.berry, 1, 4));
			OreDictionary.registerOre("dyeLime", new ItemStack(ModItems.berry, 1, 5));
			OreDictionary.registerOre("dyePink", new ItemStack(ModItems.berry, 1, 6));
			OreDictionary.registerOre("dyeGray", new ItemStack(ModItems.berry, 1, 7));
			OreDictionary.registerOre("dyeLightGray", new ItemStack(ModItems.berry, 1, 8));
			OreDictionary.registerOre("dyeCyan", new ItemStack(ModItems.berry, 1, 9));
			OreDictionary.registerOre("dyePurple", new ItemStack(ModItems.berry, 1, 10));
			OreDictionary.registerOre("dyeBlue", new ItemStack(ModItems.berry, 1, 11));
			OreDictionary.registerOre("dyeBrown", new ItemStack(ModItems.berry, 1, 12));
			OreDictionary.registerOre("dyeGreen", new ItemStack(ModItems.berry, 1, 13));
			OreDictionary.registerOre("dyeRed", new ItemStack(ModItems.berry, 1, 14));
			OreDictionary.registerOre("dyeBlack", new ItemStack(ModItems.berry, 1, 15));
			OreDictionary.registerOre("cropRaspberry", new ItemStack(ModItems.berry, 1, 14));
			OreDictionary.registerOre("cropBlueberry", new ItemStack(ModItems.berry, 1, 11));
			OreDictionary.registerOre("cropBlackberry", new ItemStack(ModItems.berry, 1, 15));
		}
	}

	public static boolean isRegisterOre(ItemStack stack) {
		if (stack.isEmpty())
			return false;
		int[] ids = OreDictionary.getOreIDs(stack);
		for (int id: ids) {
			String oreName = OreDictionary.getOreName(id);
			if (oreName == null) continue;
			if (oreName.startsWith("ore") || oreName.startsWith("gem") || oreName.startsWith("crystal"))
				return true;
			if (oreName.equals("dustRedstone") || oreName.equals("itemFlint") || oreName.equals("coal"))
				return true;
		}
		return false;
	}
	
}
