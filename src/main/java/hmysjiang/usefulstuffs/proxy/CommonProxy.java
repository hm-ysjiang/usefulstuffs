package hmysjiang.usefulstuffs.proxy;

import hmysjiang.usefulstuffs.tileentity.TileEntityRainDetector;
import hmysjiang.usefulstuffs.tileentity.TileEntityWell;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public void registerRenders() {
	}
	
	public void registerModelBakeryVariants() {
	}
	
	public void registerTileEntity() {
		GameRegistry.registerTileEntity(TileEntityWell.class, "usefulstuffs:well_of_waterfall");
		GameRegistry.registerTileEntity(TileEntityRainDetector.class, "usefulstuffs:rain_detector");
	}
	
}