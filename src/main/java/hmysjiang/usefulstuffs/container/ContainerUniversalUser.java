package hmysjiang.usefulstuffs.container;

import hmysjiang.usefulstuffs.blocks.universaluser.TileEntityUniversalUser;
import hmysjiang.usefulstuffs.container.ContainerCampfire.SlotFuel;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerUniversalUser extends ContainerBase {
	
	private FluidTank tank;
	private EnergyStorage capacitor;
	private TileEntityUniversalUser tile;

	public ContainerUniversalUser(IInventory inv, TileEntityUniversalUser tile) {
		super(inv, (ItemStackHandler) tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
		this.tank = tile.getTank();
		this.capacitor = tile.getCapacitor();
		this.tile = tile;

		this.addSlotToContainer(new SlotItemHandler(handler, 0, 62, 17));
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 80, 17));
		this.addSlotToContainer(new SlotItemHandler(handler, 2, 98, 17));
		this.addSlotToContainer(new SlotItemHandler(handler, 3, 62, 35));
		this.addSlotToContainer(new SlotItemHandler(handler, 4, 80, 35));
		this.addSlotToContainer(new SlotItemHandler(handler, 5, 98, 35));
		this.addSlotToContainer(new SlotItemHandler(handler, 6, 62, 53));
		this.addSlotToContainer(new SlotItemHandler(handler, 7, 80, 53));
		this.addSlotToContainer(new SlotItemHandler(handler, 8, 98, 53));
		
		int xPos = 8;
		int yPos = 116;
		
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(inv, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
			}
		}
		
		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(inv, x, xPos + x * 18, yPos + 58));
		}
	}
	
	public EnergyStorage getCapacitor() {
		return capacitor;
	}
	
	public FluidTank getTank() {
		return tank;
	}
	
	public TileEntityUniversalUser getTile() {
		return tile;
	}

}
