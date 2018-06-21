package hmysjiang.usefulstuffs.container.slot;

import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.items.gun.ItemBullet;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotBullet extends SlotItemHandler {

	public SlotBullet(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return super.isItemValid(stack) && stack.getItem() instanceof ItemBullet && stack.getItemDamage() != 0;
	}

}
