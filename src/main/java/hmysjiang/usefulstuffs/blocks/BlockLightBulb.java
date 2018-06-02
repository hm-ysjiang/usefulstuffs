package hmysjiang.usefulstuffs.blocks;

import hmysjiang.usefulstuffs.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLightBulb extends Block {
	
	private static final double PIXEL = 0.0625D;	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(PIXEL*6, PIXEL*6, PIXEL*6, PIXEL*10, PIXEL*10, PIXEL*10);

	public BlockLightBulb() {
		super(Material.CLOTH);
		setUnlocalizedName(Reference.ModBlocks.LIGHT_BULB.getUnlocalizedName());
		setRegistryName(Reference.ModBlocks.LIGHT_BULB.getRegistryName());
		setLightLevel(1.0F);
		setSoundType(SoundType.CLOTH);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDING_BOX;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return NULL_AABB;
	}

}