package hmysjiang.usefulstuffs.container;

import hmysjiang.usefulstuffs.blocks.campfire.TileEntityCampfire;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCampfire extends ContainerBase {
	
	protected TileEntityCampfire tile;

	public ContainerCampfire(EntityPlayer player, BlockPos pos) {
		super(player.inventory, (ItemStackHandler) ((TileEntityCampfire) player.world.getTileEntity(pos)).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN));
		
		this.tile = (TileEntityCampfire) player.world.getTileEntity(pos);

		this.addSlotToContainer(new SlotFuel(handler, 0, 80, 20));
		
		int xPos = 8;
		int yPos = 51;
		
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(inv, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
			}
		}
		
		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(inv, x, xPos + x * 18, yPos + 58));
		}
	}
	
	public TileEntityCampfire getTile() {
		return tile;
	}
	
	public class SlotFuel extends SlotItemHandler {

		public SlotFuel(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
			super(itemHandler, index, xPosition, yPosition);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			return super.isItemValid(stack) && (stack.getItem().getItemBurnTime(stack) > 0 || TileEntityFurnace.isItemFuel(stack));
		}
		
	}

}
