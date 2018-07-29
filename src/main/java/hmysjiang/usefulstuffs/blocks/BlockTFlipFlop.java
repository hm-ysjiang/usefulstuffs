package hmysjiang.usefulstuffs.blocks;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.tileentity.TileEntityTFlipFlop;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTFlipFlop extends BlockHorizontal implements ITileEntityProvider {
	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0, 0, 0, 1, 0.0625 * 2, 1);

	public BlockTFlipFlop() {
		super(new Material(MapColor.STONE));
		setUnlocalizedName(Reference.ModBlocks.T_FLIPFLOP.getUnlocalizedName());
		setRegistryName(Reference.ModBlocks.T_FLIPFLOP.getRegistryName());
		setHardness(1.0F);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		((TileEntityTFlipFlop)worldIn.getTileEntity(pos)).setSides(state);
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}
	
	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return true;
	}
	
	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		TileEntityTFlipFlop tile = (TileEntityTFlipFlop)blockAccess.getTileEntity(pos);
		EnumFacing output = tile.getOutputSide();
		boolean q = tile.shouldQOutput();
		if (output != null) {
			if (output == side) {
				return q ? 15 : 0;
			}
			else if (output.getOpposite() == side) {
				return q ? 0 : 15;
			}	
		}
		return 0;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return getStrongPower(blockState, blockAccess, pos, side);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTFlipFlop();
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex();
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDING_BOX;
	}

}
