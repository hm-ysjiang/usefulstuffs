package hmysjiang.usefulstuffs.blocks.playerdetector;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.blocks.raindetector.BlockRainDetector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityPlayerDetector extends TileEntity implements ITickable {
	
	@Override
	public void update() {
		if (this.world != null && this.getBlockType() instanceof BlockPlayerDetector) {
			((BlockPlayerDetector) this.blockType).updateState(world, pos, world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos).grow(BlockPlayerDetector.range)).size() > 0);
		}
	}

}
