package hmysjiang.usefulstuffs.blocks.well;

import java.util.ArrayList;
import java.util.List;

import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityWell extends TileEntity implements ITickable {
	
	public static final int CAPACITY = 64000;
	public static final String KEY_CONT = "Content";
	
	private int transRate;
	private WaterTank tankWater;
	private Handler handler;
	
	public TileEntityWell() {
		tankWater = new WaterTank(CAPACITY);
		transRate = 500;
		handler = new Handler();
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
		
		
		if (!world.isRemote) {
			List<int[]> blacklist = getFilteredBlocks();
			boolean filtered;
			for (int x = -3;x<=3;x++) {
				for (int z = -3;z<=3;z++) {
					BlockPos bpos = new BlockPos(pos.getX()+x, pos.getY(), pos.getZ()+z);
					TileEntity tile = world.getTileEntity(bpos);
					if (tile != null && world.getBlockState(bpos) != ModBlocks.well.getDefaultState()) { 
						for (EnumFacing facing :EnumFacing.VALUES) {
							filtered = false;
							for (int i = 0 ; i<blacklist.size() ; i++) {
								if (blacklist.get(i)[0] == world.provider.getDimension() && blacklist.get(i)[1] == bpos.getX() && blacklist.get(i)[2] == bpos.getY() && blacklist.get(i)[3] == bpos.getZ() && blacklist.get(i)[4] == facing.getIndex()) {
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
		if (filter == null || !filter.hasTagCompound() || !filter.getTagCompound().hasKey("Blocks"))
			return blocks;
		NBTTagList list = filter.getTagCompound().getTagList("Blocks", Constants.NBT.TAG_INT_ARRAY);
		for (int i = 0 ; i<list.tagCount() ; i++)
			blocks.add(list.getIntArrayAt(i));
		return blocks;
	}
	
	public class Handler extends ItemStackHandler {
		
		public Handler() {
			super(1);
		}
		
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (stack.isItemEqualIgnoreDurability(new ItemStack(ModItems.water_blacklist)))
				return super.insertItem(slot, stack, simulate);
			return stack;
		}
		
	}
	
	public class WaterTank extends FluidTank {

		private final Fluid WATER = FluidRegistry.WATER;

	    public WaterTank(int capacity)
	    {
	        super(capacity);
	    }
	    
	    @Override
	    public int fill(FluidStack resource, boolean doFill) {
	    	if (resource.getFluid() == WATER)
	    		return super.fill(resource, doFill);
	    	return resource.amount;
	    }
		
	}

}
