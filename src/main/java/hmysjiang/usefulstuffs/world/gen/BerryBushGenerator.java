package hmysjiang.usefulstuffs.world.gen;

import java.util.Random;

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
	
	public static final int[] BIOME_IDS = new int[] {1, 4, 5, 21, 23, 27, 129, 132, 155};

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		Biome biome = world.getBiome(new BlockPos(chunkX * 16, 64, chunkZ * 16));
		if (canGenInBiome(world.getBiome(new BlockPos(chunkX * 16, 64, chunkZ * 16))) && random.nextInt(30) == 0) {
			int centerX = chunkX * 16 + random.nextInt(10) + 3;
			int centerZ = chunkZ * 16 + random.nextInt(10) + 3;
			int bushes = random.nextInt(4) + 4;
			while (bushes-- > 0) {
				int x = centerX + (int) (random.nextGaussian() * 2);
				int z = centerZ + (int) (random.nextGaussian() * 2);
				int y = WorldHelper.getGroundHeight(world, x, z);
				if (y == -1) continue;
				BlockPos pos = new BlockPos(x, ++y, z);
				while (world.getBlockState(pos).getBlock() instanceof BlockBerryBush) {
					pos = new BlockPos(x, ++y, z);
				}
				if (!world.isAirBlock(pos) && !world.getBlockState(pos).getBlock().isReplaceable(world, pos)) continue;
				world.setBlockState(pos, ModBlocks.berrybushes[random.nextInt(16)].getDefaultState());
			}
		}
	}
	
	public static boolean canGenInBiome(Biome biome) {
		int id = Biome.getIdForBiome(biome);
		for (int i: BIOME_IDS) {
			if (i == id)
				return true;
		}
		return false;
	}

}
