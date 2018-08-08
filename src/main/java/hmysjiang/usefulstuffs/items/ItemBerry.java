package hmysjiang.usefulstuffs.items;

import hmysjiang.usefulstuffs.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemBerry extends ItemFood {

	public ItemBerry() {
		super(1, 0.5F, false);
		setRegistryName(Reference.ModItems.BERRY.getRegistryName());
		setUnlocalizedName(Reference.ModItems.BERRY.getUnlocalizedName());
		setHasSubtypes(true);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		for (EnumDyeColor color: EnumDyeColor.values()) {
			items.add(new ItemStack(this, 1, color.getMetadata()));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName() + "_" + EnumDyeColor.byMetadata(stack.getMetadata()).getDyeColorName();
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 12;
	}

}
