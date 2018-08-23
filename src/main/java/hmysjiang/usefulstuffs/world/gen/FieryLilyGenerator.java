package hmysjiang.usefulstuffs.world.gen;

import java.util.Random;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.init.ModBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class FieryLilyGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (world.provider.getDimension() == -1 && random.nextInt(ConfigManager.fierylilySpawnRate) == 0) {
			int x = random.nextInt(12) + 2 + (chunkX * 16);
			int z = random.nextInt(12) + 2 + (chunkZ * 16);
			int y = 35;
			for ( ; y >= 30 ; y--) {
				BlockPos pos = new BlockPos(x, y, z);
				if (world.isAirBlock(pos) && world.getBlockState(pos.down()).getMaterial() == Material.LAVA) 
					break;
			}
			if (y == 29)
				return;
			world.setBlockState(new BlockPos(x, y, z), Blocks.NETHER_BRICK_FENCE.getDefaultState());
			world.setBlockState(new BlockPos(x, y + 1, z), Blocks.NETHER_BRICK_FENCE.getDefaultState());
			world.setBlockState(new BlockPos(x, y + 2, z), Blocks.GLOWSTONE.getDefaultState());
			for (int i = -1 ; i<2 ; i++) {
				for (int j = -1 ; j<2 ; j++) {
					if (i == 0 && j == 0)
						continue;
					world.setBlockState(new BlockPos(x + i, y - 1, z + j), Blocks.MAGMA.getDefaultState());
				}
			}
			for (int i = -2 ; i<3 ; i++) {
				for (int j = -2 ; j<3 ; j++) {
					if (i == 0 && j == 0)
						continue;
					if (i*j == 4 || i*j == -4) {
						if (random.nextInt(3) == 0) {
							world.setBlockState(new BlockPos(x + i, y - 1, z + j), Blocks.LAVA.getDefaultState());
							world.setBlockState(new BlockPos(x + i, y, z + j), ModBlocks.fiery_lily.getDefaultState());
						}
					}
					else if (world.getBlockState(new BlockPos(x + i, y - 1, z + j)) != Blocks.MAGMA.getDefaultState()) {
						world.setBlockState(new BlockPos(x + i, y - 1, z + j), Blocks.LAVA.getDefaultState());
						world.setBlockState(new BlockPos(x + i, y, z + j), ModBlocks.fiery_lily.getDefaultState());
					}
				}
			}
		}
	}

}
