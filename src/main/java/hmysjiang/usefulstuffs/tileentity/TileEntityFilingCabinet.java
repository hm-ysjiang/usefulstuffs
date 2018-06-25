package hmysjiang.usefulstuffs.tileentity;

import hmysjiang.usefulstuffs.tileentity.capability.UnstackableItemStackHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityFilingCabinet extends TileEntity implements ITickable {
	
	public static final String KEY_CONT = "Contents";
	
	private UnstackableItemStackHandler handler;
	
	public TileEntityFilingCabinet() {
		handler = new UnstackableItemStackHandler(1080);
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
		handler.deserializeNBT(compound.getCompoundTag(KEY_CONT));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag(KEY_CONT, handler.serializeNBT());
		return super.writeToNBT(compound);
	}

	@Override
	public void update() {
		
	}
	
}