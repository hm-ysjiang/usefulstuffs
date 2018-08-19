package hmysjiang.usefulstuffs.world.gen;

import java.util.Random;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.blocks.bush.BlockBerryBush;
import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.utils.WorldHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class BerryBushGenerator implements IWorldGenerator {
	public static final int[] BANNED_BIOME_IDS = ConfigManager.bush_banned_biomes;
	public static final int[] BANNED_DIM_IDS = ConfigManager.bush_banned_dims;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		Biome biome = world.getBiome(new BlockPos(chunkX * 16, 64, chunkZ * 16));
		if (canGenInWorld(world) && canGenInBiome(world.getBiome(new BlockPos(chunkX * 16, 64, chunkZ * 16))) && random.nextInt(50) == 0) {
			int centerX = chunkX * 16 + random.nextInt(10) + 3;
			int centerZ = chunkZ * 16 + random.nextInt(10) + 3;
			int bushes = random.nextInt(4) + 4;
			while (bushes-- > 0) {
				int x = centerX + (int) (random.nextGaussian() * 2);
				int z = centerZ + (int) (random.nextGaussian() * 2);
				int y = WorldHelper.getGroundHeight(world, x, z);
				if (y == -1 || y <= world.getSeaLevel()) continue;
				BlockPos pos = new BlockPos(x, ++y, z);
				while (world.getBlockState(pos).getBlock() instanceof BlockBerryBush) {
					pos = new BlockPos(x, ++y, z);
				}
				if (!world.isAirBlock(pos) && !world.getBlockState(pos).getBlock().isReplaceable(world, pos)) continue;
				world.setBlockState(pos, ((BlockBerryBush) ModBlocks.berrybushes[random.nextInt(16)]).getRandomSpawnState(random));
			}
		}
	}
	
	public static boolean canGenInWorld(World world) {
		int id = world.provider.getDimension();
		for (int i: BANNED_DIM_IDS)
			if (i == id)
				return false;
		return true;
	}
	
	public static boolean canGenInBiome(Biome biome) {
		int id = Biome.getIdForBiome(biome);
		for (int i: BANNED_BIOME_IDS)
			if (i == id)
				return false;
		return true;
	}

}
