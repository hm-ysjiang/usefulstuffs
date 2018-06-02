package hmysjiang.usefulstuffs.miscs.helper;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
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
	
	public static boolean hasNoBlockBelow(World world, BlockPos pos) {
		return world.getBlockState(new BlockPos(pos.getX(), pos.getY()-1, pos.getZ())).getMaterial() == Material.AIR;
	}
	
	public static void updateLightInArea(World worldIn, double posX, double posY, double posZ) {
//		worldIn.markBlockRangeForRenderUpdate(new BlockPos(posX, posY, posZ), new BlockPos(posX, posY, posZ));
//		worldIn.notifyBlockUpdate(new BlockPos(posX, posY, posZ), worldIn.getBlockState(new BlockPos(posX, posY, posZ)), worldIn.getBlockState(new BlockPos(posX, posY, posZ)), 8);
		worldIn.checkLightFor(EnumSkyBlock.BLOCK, new BlockPos(
				posX, posY + 1, posZ));
		worldIn.checkLightFor(EnumSkyBlock.BLOCK, new BlockPos(
				posX, posY - 1, posZ));
		worldIn.checkLightFor(EnumSkyBlock.BLOCK, new BlockPos(
				posX + 1, posY, posZ));
		worldIn.checkLightFor(EnumSkyBlock.BLOCK, new BlockPos(
				posX - 1, posY, posZ));
		worldIn.checkLightFor(EnumSkyBlock.BLOCK, new BlockPos(
				posX, posY, posZ + 1));
		worldIn.checkLightFor(EnumSkyBlock.BLOCK, new BlockPos(
				posX, posY, posZ - 1));
	}
	
	public static BlockPos[] getAdjacentBlockPos(BlockPos pos) {
		return new BlockPos[] {
				new BlockPos(pos.getX()+1, pos.getY(), pos.getZ()),
				new BlockPos(pos.getX()-1, pos.getY(), pos.getZ()),
				new BlockPos(pos.getX(), pos.getY()+1, pos.getZ()),
				new BlockPos(pos.getX(), pos.getY()-1, pos.getZ()),
				new BlockPos(pos.getX(), pos.getY(), pos.getZ()+1),
				new BlockPos(pos.getX(), pos.getY(), pos.getZ()-1)
				};
	}
	
}
