package hmysjiang.usefulstuffs.items;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.blocks.bush.EnumBerryColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemBerry extends ItemFood {

	public ItemBerry() {
		super(1, 0.125F, false);
		setRegistryName(Reference.ModItems.BERRY.getRegistryName());
		setUnlocalizedName(Reference.ModItems.BERRY.getUnlocalizedName());
		setHasSubtypes(true);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab == UsefulStuffs.TAB) {
			for (EnumBerryColor color: EnumBerryColor.values()) {
				items.add(new ItemStack(this, 1, color.getMetadata()));
			}	
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName() + "_" + EnumBerryColor.byMetadata(stack.getMetadata()).getDyeColorName();
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 12;
	}

}
