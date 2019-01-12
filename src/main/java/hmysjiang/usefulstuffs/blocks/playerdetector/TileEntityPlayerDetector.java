package hmysjiang.usefulstuffs.blocks.playerdetector;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityPlayerDetector extends TileEntity implements ITickable {
	
	@Override
	public void update() {
		if (this.world != null && this.getBlockType() instanceof BlockPlayerDetector) {
			((BlockPlayerDetector) this.blockType).updateState(world, pos, false);
		}
	}

}
