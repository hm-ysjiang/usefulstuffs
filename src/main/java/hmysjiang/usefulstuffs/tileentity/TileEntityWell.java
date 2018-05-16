package hmysjiang.usefulstuffs.tileentity;

import java.util.ArrayList;
import java.util.List;

import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.items.ItemWaterBlackListFilter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityWell extends TileEntity implements ITickable {
	
	public static final int CAPACITY = 64000;
	public static final String KEY_CONT = "Content";
	
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
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing == EnumFacing.UP)
			return (T) handler;
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
		
		
		if (!worldObj.isRemote) {
			List<int[]> blacklist = getFilteredBlocks();
			boolean filtered;
			for (int x = -3;x<=3;x++) {
				for (int z = -3;z<=3;z++) {
					BlockPos bpos = new BlockPos(pos.getX()+x, pos.getY(), pos.getZ()+z);
					TileEntity tile = worldObj.getTileEntity(bpos);
					if (tile != null && worldObj.getBlockState(bpos) != ModBlocks.well.getDefaultState()) { 
						for (EnumFacing facing :EnumFacing.VALUES) {
							filtered = false;
							for (int i = 0 ; i<blacklist.size() ; i++) {
								if (blacklist.get(i)[0] == worldObj.provider.getDimension() && blacklist.get(i)[1] == bpos.getX() && blacklist.get(i)[2] == bpos.getY() && blacklist.get(i)[3] == bpos.getZ() && blacklist.get(i)[4] == facing.getIndex()) {
									filtered = true;
									break;
								}
							}
							if (!filtered && tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)) {
								tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing).fill(new FluidStack(FluidRegistry.WATER, transRate), true);
								break;
							}
						}
					}
				}
			}	
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.handler.deserializeNBT(compound.getCompoundTag(KEY_CONT));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag(KEY_CONT, handler.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	public int getFluidAmount() {
		return tankWater.getFluidAmount();
	}
	
	public int getTransferRate() {
		return transRate;
	}
	
	public List<int[]> getFilteredBlocks(){
		List<int[]> blocks = new ArrayList<int[]>();
		ItemStack filter = handler.getStackInSlot(0);
		if (filter == null || !filter.hasTagCompound() || !filter.getTagCompound().hasKey(ItemWaterBlackListFilter.KEY_BLOCKS))
			return blocks;
		NBTTagList list = filter.getTagCompound().getTagList(ItemWaterBlackListFilter.KEY_BLOCKS, Constants.NBT.TAG_INT_ARRAY);
		for (int i = 0 ; i<list.tagCount() ; i++)
			blocks.add(list.getIntArrayAt(i));
		return blocks;
	}

}