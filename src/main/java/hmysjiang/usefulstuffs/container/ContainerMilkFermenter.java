package hmysjiang.usefulstuffs.container;

import hmysjiang.usefulstuffs.blocks.fermenter.TileEntityMilkFermenter;
import hmysjiang.usefulstuffs.container.ContainerCampfire.SlotFuel;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMilkFermenter extends ContainerBase {
	
	private TileEntityMilkFermenter tile;

	public ContainerMilkFermenter(EntityPlayer player, BlockPos pos) {
		super(player.inventory, (ItemStackHandler) ((TileEntityMilkFermenter)player.world.getTileEntity(pos)).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
		
		tile = (TileEntityMilkFermenter)player.world.getTileEntity(pos);

		this.addSlotToContainer(new SlotMilkBag(handler, 0, 54, 48));
		this.addSlotToContainer(new SlotMilkBag(handler, 1, 80, 30));
		this.addSlotToContainer(new SlotMilkBag(handler, 2, 106, 48));
		
		int xPos = 8;
		int yPos = 84;
		
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(inv, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
			}
		}
		
		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(inv, x, xPos + x * 18, yPos + 58));
		}
	}
	
	public TileEntityMilkFermenter getTile() {
		return tile;
	}
	
	public class SlotMilkBag extends SlotItemHandler {

		public SlotMilkBag(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
			super(itemHandler, index, xPosition, yPosition);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			return super.isItemValid(stack) && stack.isItemEqualIgnoreDurability(new ItemStack(ModItems.milk_bag));
		}
		
	}

}
