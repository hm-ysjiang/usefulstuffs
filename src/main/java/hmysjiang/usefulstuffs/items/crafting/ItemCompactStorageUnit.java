package hmysjiang.usefulstuffs.items.crafting;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiConfigEntries.ChatColorEntry;

public class ItemCompactStorageUnit extends ItemIngredient {
	
	public ItemCompactStorageUnit() {
		setUnlocalizedName(Reference.ModItems.CSU.getUnlocalizedName());
		setRegistryName(Reference.ModItems.CSU.getRegistryName());
	}
	
}
