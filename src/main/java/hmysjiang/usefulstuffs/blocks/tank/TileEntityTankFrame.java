package hmysjiang.usefulstuffs.blocks.tank;

import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.items.ItemTankContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityTankFrame extends TileEntity implements ITickable {
	
	private Handler handler;
	
	public TileEntityTankFrame() {
		handler = new Handler();
	}
	
	public void setTank(ItemStack tank) {
		if (tank.isEmpty() || tank.getItem() == ModItems.tank_container) {
			this.handler.setItem(tank);
			world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockTankFrame.TIER, tank.isEmpty() ? ItemTankContainer.TankTier.NONE : ItemTankContainer.TankTier.byMeta(tank.getMetadata())));
		}
	}
	
	public ItemStack getTank() {
		return handler.getItem();
	}
	
	public int getTankTier() {
		ItemStack tank = getTank();
		if (tank.isEmpty())
			return 5;
		return tank.getMetadata();
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			this.callUpdate();
			IBlockState state = world.getBlockState(pos);
			if (state.getValue(BlockTankFrame.TIER).availiable()) {
				if (state.getValue(BlockTankFrame.TIER).getMeta() != getTankTier()) {
					world.setBlockState(pos, state.withProperty(BlockTankFrame.TIER, ItemTankContainer.TankTier.byMeta(getTankTier())));
				}
			}
			else if (!getTank().isEmpty()) {
				world.setBlockState(pos, state.withProperty(BlockTankFrame.TIER, ItemTankContainer.TankTier.byMeta(getTankTier())));
			}
		}
	}
	
	public void callUpdate() {
		this.world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		this.world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
		this.markDirty();
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return getCapability(capability, facing) != null;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			ItemStack tank = this.handler.getItem();
			if (!tank.isEmpty()) {
				return (T) tank.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			}
		}
		else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) handler;
		return null;
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 1, writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		handler.deserializeNBT(compound.getCompoundTag("Cont"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("Cont", handler.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}
	
	public static class Handler extends ItemStackHandler {
		
		public Handler() {
			super(1);
		}
		
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (!this.isItemValid(0, stack))
				return stack;
			return super.insertItem(slot, stack, simulate);
		}
		
		public ItemStack getItem() {
			return this.getStackInSlot(0);
		}
		
		public void setItem(ItemStack stack) {
			this.setStackInSlot(0, stack);
		}
		
		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			return super.isItemValid(slot, stack) && stack.getItem() == ModItems.tank_container;
		}
		
	}
	
}
