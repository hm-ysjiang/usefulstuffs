package hmysjiang.usefulstuffs.blocks.portalmuffler;

import hmysjiang.usefulstuffs.utils.TileEntityManager;
import hmysjiang.usefulstuffs.utils.TileEntityManagerClient;

public class TileEntityPortalMuffler extends TileEntityManagerClient {

	@Override
	protected TileEntityManager getTileEntityManager() {
		return PortalMufflerManager.INSTANCE;
	}
	
}
