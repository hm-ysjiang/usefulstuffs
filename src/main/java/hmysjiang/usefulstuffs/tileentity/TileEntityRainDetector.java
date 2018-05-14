package hmysjiang.usefulstuffs.tileentity;

import hmysjiang.usefulstuffs.blocks.BlockRainDetector;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityRainDetector extends TileEntity implements ITickable {

	@Override
	public void update() {
		if (this.worldObj != null && !this.worldObj.isRemote && this.getBlockType() instanceof BlockRainDetector) {
			((BlockRainDetector)this.blockType).updateState(this.worldObj, this.pos);
		}
	}

}
