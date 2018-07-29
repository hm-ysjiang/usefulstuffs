package hmysjiang.usefulstuffs.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGluedBox extends TileEntity {
	
	private NBTTagCompound content;
	
	public TileEntityGluedBox() {
		content = new NBTTagCompound();
	}
	
	public void setContent(NBTTagCompound compound) {
		this.content = compound;
	}
	
	public NBTTagCompound getContent() {
		return content;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		content = compound.getCompoundTag("Cont");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("Cont", content);
		return super.writeToNBT(compound);
	}

}
