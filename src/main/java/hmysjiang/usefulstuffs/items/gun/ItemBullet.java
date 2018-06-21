package hmysjiang.usefulstuffs.items.gun;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.items.variants.EnumBulletVariants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBullet extends Item {
	
	public ItemBullet() {
		setUnlocalizedName(Reference.ModItems.BULLET.getUnlocalizedName());
		setRegistryName(Reference.ModItems.BULLET.getRegistryName());
		setHasSubtypes(true);
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for (int i = 0 ; i<EnumBulletVariants.values().length ; i++) {
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		for (int i = 0 ; i<EnumBulletVariants.values().length ; i++) {
			if (stack.getItemDamage() == i) {
				return this.getUnlocalizedName() + '.' + EnumBulletVariants.values()[i].getName();
			}
		}
		return this.getUnlocalizedName() + '.' + EnumBulletVariants.values()[0].getName();
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		if (!(stack.getItem() instanceof ItemBullet))
			return 64;
		switch(stack.getItemDamage()) {
		//shell
		case 0:
			return 64;
		//hand gun
		case 1:
			return 7;
		//shot gun
		case 2:
			return 5;
		//rifle
		case 3:
			return 30;
		//rail gun
		case 4:
			return 1;
		//err
		default:
			return 64;
		}
	}
	
}
