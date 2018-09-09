package hmysjiang.usefulstuffs.blocks.fermenter;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.items.ItemMilkBag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMilkFermenter extends TileEntity implements ITickable, ICapabilityProvider {
	
	private static int workTimeSeq = -1;
	private ItemStackHandler handler;
	private int workTime;
	
	public TileEntityMilkFermenter() {
		handler = new Handler(3);
		workTime = 0;
		if (workTimeSeq == -1)
			workTimeSeq = ConfigManager.milkFermentTime / 100;
	}

	@Override
	public void update() {
		if (world.isRemote) return;
		if (!handler.getStackInSlot(0).isEmpty() && handler.getStackInSlot(1).isEmpty() && handler.getStackInSlot(2).isEmpty() && !ItemMilkBag.isCompletelyFermented(handler.getStackInSlot(0)) && handler.getStackInSlot(0).getItemDamage() < 8) {
			handler.setStackInSlot(1, handler.getStackInSlot(0));
			handler.setStackInSlot(0, ItemStack.EMPTY);
		}
		ItemStack stack = handler.getStackInSlot(1);
		if (stack.isItemEqualIgnoreDurability(new ItemStack(ModItems.milk_bag))) {
			if (stack.isEmpty()) {
				workTime = 0;
			}
			else {
				if (stack.getItemDamage() == 8) return;
				workTime++;
				workTime %= workTimeSeq * (8 - stack.getItemDamage());
				if (workTime == 0) {
					if (stack.isItemEqualIgnoreDurability(new ItemStack(ModItems.milk_bag))) {
						if (!stack.hasTagCompound()) ItemMilkBag.setDefaultTag(stack);
						NBTTagCompound compound = stack.getTagCompound();
						int level = compound.getInteger("FermentLevel") + 1;
						compound.setInteger("FermentLevel", level);
						if (level >= 100) {
							handler.setStackInSlot(2, stack);
							handler.setStackInSlot(1, ItemStack.EMPTY);
						}
					}
				}
			}
		}
	}

	public boolean isWorking() {
		if (!handler.getStackInSlot(1).isEmpty() && handler.getStackInSlot(2).isEmpty()) {
			ItemStack stack = handler.getStackInSlot(1);
			if (stack.getItemDamage() == 8 || !stack.hasTagCompound() || stack.getTagCompound().getInteger("FermentLevel") >= 100)
				return false;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return getCapability(capability, facing) != null;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) handler;
		return null;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		handler.deserializeNBT(compound.getCompoundTag("Cont"));
		workTime = compound.getInteger("Work");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("Cont", handler.serializeNBT());
		compound.setInteger("Work", workTime);
		return super.writeToNBT(compound);
	}
	
	public static class Handler extends ItemStackHandler {
		
		public Handler(int size) {
			super(size);
		}
		
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (slot == 0)
				return super.insertItem(slot, stack, simulate);
			return stack;
		}
		
	}

}
