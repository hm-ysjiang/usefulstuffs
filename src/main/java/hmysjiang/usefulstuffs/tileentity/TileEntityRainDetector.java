package hmysjiang.usefulstuffs.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityRainDetector extends TileEntity implements ITickable {
	
	private boolean bufIsRaining;
	
	public TileEntityRainDetector() {
		bufIsRaining = (this.worldObj != null && !this.worldObj.isRemote) ? this.worldObj.isRaining() : false ;
	}

	@Override
	public void update() {
		if (this.worldObj != null && !this.worldObj.isRemote) {
			if (this.worldObj.isRaining()) {
				
			}
		}
	}

}
