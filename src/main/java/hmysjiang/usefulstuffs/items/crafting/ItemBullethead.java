package hmysjiang.usefulstuffs.items.crafting;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.items.variants.EnumBulletHead;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBullethead extends ItemIngredient {
	
	public ItemBullethead() {
		setUnlocalizedName(Reference.ModItems.BULLET_HEAD.getUnlocalizedName());
		setRegistryName(Reference.ModItems.BULLET_HEAD.getRegistryName());
		setHasSubtypes(true);
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for (int i = 0 ; i<EnumBulletHead.values().length ; i++) {
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		for (int i = 0 ; i<EnumBulletHead.values().length ; i++) {
			if (stack.getItemDamage() == i) {
				return this.getUnlocalizedName() + '.' + EnumBulletHead.values()[i].getName();
			}
		}
		return this.getUnlocalizedName() + '.' + EnumBulletHead.values()[0].getName();
	}
	
}
