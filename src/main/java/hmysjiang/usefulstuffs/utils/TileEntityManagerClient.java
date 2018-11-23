package hmysjiang.usefulstuffs.utils;

import javax.annotation.Nonnull;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public abstract class TileEntityManagerClient extends TileEntity implements ITickable {
	
	protected boolean registered = false;
	
	@Nonnull
	protected abstract TileEntityManager getTileEntityManager();
	
	@Override
	public void invalidate() {
		if (world != null && getTileEntityManager().onCorrectSide(world) && this.registered)
			getTileEntityManager().unregisterClient(this);
		super.invalidate();
	}

	@Override
	public void update() {
		if (world != null && getTileEntityManager().onCorrectSide(world) && !this.registered && (this.world.provider.getDimension() == 0 || this.world.provider.getDimension() == -1))
			registered = getTileEntityManager().registerClient(this);
	}
	
	@Override
	public void onChunkUnload() {
		if (world != null && getTileEntityManager().onCorrectSide(world) && this.registered)
			getTileEntityManager().unregisterClient(this);
	}
	
}
