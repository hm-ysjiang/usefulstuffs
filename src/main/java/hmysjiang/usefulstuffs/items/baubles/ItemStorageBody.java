package hmysjiang.usefulstuffs.items.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import hmysjiang.usefulstuffs.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStorageBody extends Item implements IBauble {
	
	public ItemStorageBody() {
		setUnlocalizedName(Reference.ModItems.BODY_STORAGE.getUnlocalizedName());
		setRegistryName(Reference.ModItems.BODY_STORAGE.getRegistryName());
		setMaxStackSize(1);
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.BODY;
	}

}
