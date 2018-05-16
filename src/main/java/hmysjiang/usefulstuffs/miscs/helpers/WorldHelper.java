package hmysjiang.usefulstuffs.miscs.helpers;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class WorldHelper {

	/**
	 * Check if a block can see sky, transparent blocks doesn't count
	 * Do not check the world is remote or not
	 * @param worldIn
	 * @param pos
	 * @return
	 */
	public static boolean canBlockSeeSky(World worldIn, BlockPos pos) {
		int max_h = worldIn.getHeight();
		for (int y = pos.getY()+1 ; y<max_h ; y++) 
			if (worldIn.getBlockState(new BlockPos(pos.getX(), y, pos.getZ())).getMaterial() != Material.AIR) 
				return false;
		return true;
	}
	
	public static boolean isWorldRainingOrGoingTo(World worldIn) {
		return worldIn.rainingStrength > 0;
	}
	
	public static World getWorldFromId(int id) {
		for (WorldServer world:DimensionManager.getWorlds()) {
			if (world.provider.getDimension() == id) {
				return world;
			}
		}
		return null;
	}
	
}
