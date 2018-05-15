package hmysjiang.usefulstuffs.tileentity;

import java.util.ArrayList;

import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileEntityWell extends TileEntity implements ITickable {
	
	public static final int CAPACITY = 64000;
	private int transRate;
	
	private WaterTank tankWater;
	private FilteredItemStackHandler handler;
	
	public TileEntityWell() {
		tankWater = new WaterTank(CAPACITY);
		transRate = 500;
		handler = new FilteredItemStackHandler().setFilteredItem(ModItems.waterfilter);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return (T) tankWater;
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return this.getCapability(capability, facing) != null;
	}

	@Override
	public void update() {
		if (tankWater.getFluidAmount() < CAPACITY) {
			tankWater.fill(new FluidStack(FluidRegistry.WATER, 1000), true);
		}
		
		for (int x = -2;x<=2;x++) {
			for (int z = -2;z<=2;z++) {
				BlockPos bpos = new BlockPos(pos.getX()+x, pos.getY(), pos.getZ()+z);
				TileEntity tile = worldObj.getTileEntity(bpos);
				if (tile != null && worldObj.getBlockState(bpos) != ModBlocks.well.getDefaultState()) 
					for (EnumFacing facing :EnumFacing.VALUES) 
						if (tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)) {
							tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing).fill(new FluidStack(FluidRegistry.WATER, transRate), true);
							break;
						}
			}
		}
	}
	
	public int getFluidAmount() {
		return tankWater.getFluidAmount();
	}
	
	public int getTransferRate() {
		return transRate;
	}	

}