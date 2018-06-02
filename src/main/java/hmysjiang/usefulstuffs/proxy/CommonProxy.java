package hmysjiang.usefulstuffs.proxy;

import hmysjiang.usefulstuffs.tileentity.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public void registerRenders() {
	}
	
	public void registerModelBakeryVariants() {
	}
	
	public void registerTileEntity() {
		GameRegistry.registerTileEntity(TileEntityWell.class, "usefulstuffs:well_of_waterfall");
		GameRegistry.registerTileEntity(TileEntityRainDetector.class, "usefulstuffs:rain_detector");
		GameRegistry.registerTileEntity(TileEntityCampfire.class, "usefulstuffs:campfire");
		GameRegistry.registerTileEntity(TileEntityLantern.class, "usefulstuffs:lantern");
		GameRegistry.registerTileEntity(TileEntityFilingCabinet.class, "usefulstuffs:filing_cabinet");
	}
	
}