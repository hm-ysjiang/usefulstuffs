package hmysjiang.usefulstuffs.blocks.materials;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockMaterials {
	
	@Deprecated
	public BlockMaterials() {}
	
	public static class Campfire extends Material{
		public Campfire() {
			super(MapColor.WOOD);
			setImmovableMobility();
		}
	}
	
	public static class FilingCabinet extends Material{
		public FilingCabinet() {
			super(MapColor.WOOD);
			setImmovableMobility();
		}
	}
	
	public static class LightBulb extends Material{
		public LightBulb() {
			super(MapColor.GOLD);
			setImmovableMobility();
			setReplaceable();
		}
	}
	
	public static class GluedBox extends Material{
		public GluedBox() {
			super(MapColor.YELLOW);
			setImmovableMobility();
		}
	}

}
