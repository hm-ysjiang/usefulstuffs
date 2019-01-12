package hmysjiang.usefulstuffs.blocks.filingcabinets;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityFilingCabinetUnstackable extends TileEntity {

	private Handler handler;
	
	public TileEntityFilingCabinetUnstackable() {
		handler = new Handler(1080);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) handler;
		return null;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return getCapability(capability, facing) != null;
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
	
	public void sort() {
		this.handler.sort();
		this.markDirty();
	}
	
	public class Handler extends ItemStackHandler {

		public Handler(int size) {
			super(size);
		}
		
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (stack.isStackable())
				return stack;
			return super.insertItem(slot, stack, simulate);
		}
		
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			ItemStack toReturn = super.extractItem(slot, amount, simulate);
			return toReturn;
		}
		
		public void sort() {
			NonNullList<ItemStack> stacksNew = NonNullList.<ItemStack>withSize(this.getSlots(), ItemStack.EMPTY);
			int p = 0;
			for (ItemStack stack: stacks) {
				if (!stack.isEmpty()) {
					stacksNew.set(p++, stack);
				}
			}
			this.stacks = stacksNew;
		}
		
	}
	
}
