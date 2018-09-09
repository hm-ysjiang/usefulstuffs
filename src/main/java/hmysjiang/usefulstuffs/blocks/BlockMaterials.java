package hmysjiang.usefulstuffs.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockMaterials {
	
	public static final Material CAMPFIRE = new CAMPFIRE();
	public static final Material FILINGCABINET = new FilingCabinet();
	public static final Material LIGHTBULB = new LightBulb();
	public static final Material GLUEDBOX = new GluedBox();
	public static final Material MACHINE = new Machine();
	
	@Deprecated
	public BlockMaterials() {}
	
	private static class CAMPFIRE extends Material{
		public CAMPFIRE() {
			super(MapColor.WOOD);
			setImmovableMobility();
		}
	}
	
	protected static class FilingCabinet extends Material{
		public FilingCabinet() {
			super(MapColor.WOOD);
			setImmovableMobility();
		}
	}
	
	protected static class LightBulb extends Material{
		public LightBulb() {
			super(MapColor.GOLD);
			setImmovableMobility();
		}
	}
	
	protected static class GluedBox extends Material{
		public GluedBox() {
			super(MapColor.YELLOW);
			setImmovableMobility();
		}
	}
	
	protected static class Machine extends Material{
		public Machine() {
			super(MapColor.STONE);
			setImmovableMobility();
		}
	}
}
