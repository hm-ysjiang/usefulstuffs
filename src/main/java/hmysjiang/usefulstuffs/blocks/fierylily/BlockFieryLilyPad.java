package hmysjiang.usefulstuffs.blocks.fierylily;

import java.util.Random;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFieryLilyPad extends BlockLilyPad {

	public BlockFieryLilyPad(boolean enabled) {
		setUnlocalizedName(Reference.ModBlocks.FIERY_LILY.getUnlocalizedName());
		setRegistryName(Reference.ModBlocks.FIERY_LILY.getRegistryName());
		if (enabled)
			ModItems.itemblocks.add(new ItemFieryLilyPad(this).setRegistryName(getRegistryName()));
		setSoundType(SoundType.PLANT);
	}
	
	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == Blocks.LAVA;
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getBlock() == Blocks.LAVA && ((Integer)worldIn.getBlockState(pos).getValue(BlockLiquid.LEVEL)).intValue() == 0 && worldIn.isAirBlock(pos.up());
	}
	
	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		if (pos.getY() >= 0 && pos.getY() < 256) {
			IBlockState iblockstate = worldIn.getBlockState(pos.down());
			return iblockstate.getMaterial() == Material.LAVA && ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0;
		}
		return false;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return super.getMetaFromState(state);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (this.blockMaterial == Material.LAVA && worldIn.getBlockState(pos.up()).getMaterial() == Material.AIR && !worldIn.getBlockState(pos.up()).isOpaqueCube()) {
			if (rand.nextInt(100) == 0) {
				double x = (double)pos.getX() + (double)rand.nextFloat();
				double y = (double)pos.getY() + stateIn.getBoundingBox(worldIn, pos).maxY;
				double z = (double)pos.getZ() + (double)rand.nextFloat();
				worldIn.spawnParticle(EnumParticleTypes.LAVA, x, y, z, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
}
