package hmysjiang.usefulstuffs.blocks.raindetector;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityRainDetector extends TileEntity implements ITickable {

	@Override
	public void update() {
		if (this.world != null && !this.world.isRemote && this.getBlockType() instanceof BlockRainDetector) {
			((BlockRainDetector)this.blockType).updateState(this.world, this.pos);
		}
	}

}
