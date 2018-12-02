package hmysjiang.usefulstuffs.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockMaterials {
	
	public static final Material CAMPFIRE = new CAMPFIRE();
	public static final Material LIGHTBULB = new LightBulb();
	public static final Material GLUEDBOX = new GluedBox();
	
	@Deprecated
	public BlockMaterials() {}
	
	private static class CAMPFIRE extends Material{
		public CAMPFIRE() {
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
}
