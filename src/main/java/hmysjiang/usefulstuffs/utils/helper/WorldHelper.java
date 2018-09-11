package hmysjiang.usefulstuffs.utils.helper;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableMap;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.items.ItemPackingGlue;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.items.IItemHandler;

public class WorldHelper {
	
	private static final int bush_min_h = ConfigManager.bushSpawnMinHeight;
	
	public static World getServerWorldFromId(int id) {
		for (WorldServer world:DimensionManager.getWorlds()) {
			if (world.provider.getDimension() == id) {
				return world;
			}
		}
		return null;
	}
	
	public static EnumFacing getRelationBetweenAdjacentBlocks(BlockPos dominant, BlockPos recessive) {
		if (dominant.getX() != recessive.getX()) {
			if (dominant.getY() != recessive.getY() || dominant.getZ() != recessive.getZ()) return null;
			if (dominant.getX() > recessive.getX()) return EnumFacing.WEST;
			else return EnumFacing.EAST;
		}
		else if (dominant.getY() != recessive.getY()) {
			if (dominant.getX() != recessive.getX() || dominant.getZ() != recessive.getZ()) return null;
			if (dominant.getY() > recessive.getY()) return EnumFacing.DOWN;
			else return EnumFacing.UP;
		}
		else if (dominant.getZ() != recessive.getZ()) {
			if (dominant.getX() != recessive.getX() || dominant.getY() != recessive.getY()) return null;
			if (dominant.getZ() > recessive.getZ()) return EnumFacing.NORTH;
			else return EnumFacing.SOUTH;
		}
		else return null;
	}
	
	public static boolean isRelationCorrect(BlockPos dominant, BlockPos recessive, EnumFacing relation, boolean collideCount) {
		switch (relation.getAxis()) {
		case X:
			if (dominant.getX() == recessive.getX())
				return collideCount;
			if (dominant.getX() > recessive.getX() && relation == EnumFacing.WEST) 
				return true;
			if (dominant.getX() < recessive.getX() && relation == EnumFacing.EAST) 
				return true;
			return false;
		case Y:
			if (dominant.getY() == recessive.getY())
				return collideCount;
			if (dominant.getY() > recessive.getY() && relation == EnumFacing.DOWN) 
				return true;
			if (dominant.getY() < recessive.getY() && relation == EnumFacing.UP) 
				return true;
			return false;
		case Z:
			if (dominant.getZ() == recessive.getZ())
				return collideCount;
			if (dominant.getZ() > recessive.getZ() && relation == EnumFacing.NORTH) 
				return true;
			if (dominant.getZ() < recessive.getZ() && relation == EnumFacing.SOUTH) 
				return true;
			return false;
		}
		return false;
	}
	
	/***
	 * 
	 * @param state
	 * @param tile
	 * @return the durability cost to pick up a block, used in {@link ItemPackingGlue#onItemUseFirst()}
	 */
	public static float getBlockDataDensity(World world, BlockPos pos, IBlockState state, TileEntity tile) {
		float den = 0;
		if (state != null) {
			//Block Hardness
			float hardness = state.getBlockHardness(world, pos);
			den += Math.log10(hardness + 2) / 0.7 * 10;
			
			//Block States
			ImmutableMap<IProperty<?>, Comparable<?>> properties = state.getProperties();
			for (IProperty<?> property : properties.keySet()) {
				if (property instanceof PropertyBool) {
					den += 1;
				}
				else if (property instanceof PropertyInteger) {
					den += 2;
				}
				else if (property instanceof PropertyDirection) {
					den += 3;
				}
				else {
					den += 4;
				}
			}
			
			//Extra cost for tile entities
			if (tile != null) {
				den *= 2;
			}
		}
		return den;
	}
	
	public static int getGroundHeight(World world, int x, int z) {
		if (!world.isAreaLoaded(new BlockPos(x, 64, z), 1)) return -1;
		for (int y = world.getHeight() ; y>=bush_min_h ; y--) {
			if (world.getBlockState(new BlockPos(x, y, z)) == Blocks.STONE.getDefaultState() || world.getBlockState(new BlockPos(x, y, z)) == Blocks.DIRT.getDefaultState() || world.getBlockState(new BlockPos(x, y, z)) == Blocks.GRASS.getDefaultState())
				return y;
		}
		return -1;
	}
	
	public static void spawnItemsInHandler(World world, int x, int y, int z, @Nonnull IItemHandler handler) {
		if (world.isRemote) return;
		for (int i = 0 ; i<handler.getSlots() ; i++) {
			world.spawnEntity(new EntityItem(world, x, y, z, handler.getStackInSlot(i).copy()));
		}
	}
	
	public static RayTraceResult rayTrace(EntityLivingBase living, int maxRange) {
		Vec3d prev = living.getPositionVector().add(new Vec3d(0, living.getEyeHeight(), 0));
		Vec3d startPos = new Vec3d(prev.x, prev.y, prev.z);
		Vec3d gaze = living.getLookVec().scale(0.05D);
		for (int i = 1 ; i<=maxRange * 20 ; i++) {
			Vec3d pos = startPos.add(gaze.scale(i));
			if (!living.world.isAreaLoaded(new BlockPos(pos), 1)) return null;
			if (living.world.getBlockState(new BlockPos(pos)).getMaterial() != Material.AIR) {
				if (getRelationBetweenAdjacentBlocks(new BlockPos(pos), new BlockPos(prev)) == null)
					return null;
				return new RayTraceResult(pos, getRelationBetweenAdjacentBlocks(new BlockPos(pos), new BlockPos(prev)), new BlockPos(pos));
			}
			if (!pos.equals(prev)) {
				prev = startPos.add(gaze.scale(i));
			}
		}
		return null;
	}
	
	public static int getDistanceProjctedOnAxis(BlockPos pos1, BlockPos pos2, EnumFacing.Axis axis) {
		switch (axis) {
		case X:
			return Math.abs(pos1.getX() - pos2.getX());
		case Y:
			return Math.abs(pos1.getY() - pos2.getY());
		case Z:
			return Math.abs(pos1.getZ() - pos2.getZ());
		}
		return 0;
	}
	
}
