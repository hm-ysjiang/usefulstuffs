package hmysjiang.usefulstuffs.blocks.portalmuffler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityPortalMuffler extends TileEntity implements ITickable {
	
	protected boolean registered = false;
	
	@Override
	public void invalidate() {
		if (world != null && world.isRemote && this.registered)
			PortalMufflerManager.INSTANCE.unregisterMuffler(this);
		super.invalidate();
	}

	@Override
	public void update() {
		if (world != null && world.isRemote && !this.registered && (this.world.provider.getDimension() == 0 || this.world.provider.getDimension() == -1))
			registered = PortalMufflerManager.INSTANCE.registerMuffler(this);
	}
	
	@Override
	public void onChunkUnload() {
		if (world != null && world.isRemote && this.registered)
			PortalMufflerManager.INSTANCE.unregisterMuffler(this);
	}
	
}
