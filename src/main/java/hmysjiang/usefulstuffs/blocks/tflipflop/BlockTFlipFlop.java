package hmysjiang.usefulstuffs.blocks.tflipflop;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.utils.helper.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTFlipFlop extends BlockHorizontal {

	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0, 0, 0, 1, 0.0625 * 2, 1);
	public static final PropertyBool OUTPUT = PropertyBool.create("output");

	public BlockTFlipFlop(boolean enabled) {
		super(Material.ROCK);
		setUnlocalizedName(Reference.ModBlocks.T_FLIPFLOP.getUnlocalizedName());
		setRegistryName(Reference.ModBlocks.T_FLIPFLOP.getRegistryName());
		if (enabled)
			ModItems.itemblocks.add(new ItemBlock(this).setRegistryName(getRegistryName()));
		setHardness(1.0F);
		setDefaultState(this.blockState.getBaseState().withProperty(OUTPUT, false));
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
		return side != EnumFacing.DOWN && side != EnumFacing.UP;
	}
	
	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		EnumFacing output = blockState.getValue(OUTPUT) ? blockState.getValue(FACING).rotateY() : blockState.getValue(FACING).rotateYCCW();
		return side == output ? 15 : 0;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return getStrongPower(blockState, blockAccess, pos, side);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		EnumFacing facing = state.getValue(FACING);
		worldIn.notifyNeighborsOfStateChange(pos, this, false);
		worldIn.notifyNeighborsOfStateChange(pos.offset(facing.rotateY()), this, false);
		worldIn.notifyNeighborsOfStateChange(pos.offset(facing.rotateYCCW()), this, false);
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		EnumFacing facing = state.getValue(FACING);
		worldIn.notifyNeighborsOfStateChange(pos, this, false);
		worldIn.notifyNeighborsOfStateChange(pos.offset(facing.rotateY()), this, false);
		worldIn.notifyNeighborsOfStateChange(pos.offset(facing.rotateYCCW()), this, false);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (worldIn != null && !worldIn.isRemote) {
			EnumFacing from = WorldHelper.getRelationBetweenAdjacentBlocks(pos, fromPos);
			EnumFacing facing = state.getValue(FACING);
			if (from == facing) {
				IBlockState inputState = worldIn.getBlockState(fromPos);
				if (worldIn.getRedstonePower(fromPos, facing.getOpposite()) > 0) {
					worldIn.setBlockState(pos, state.cycleProperty(OUTPUT));
					worldIn.notifyNeighborsOfStateChange(pos, this, false);
					worldIn.notifyNeighborsOfStateChange(pos.offset(facing.rotateY()), this, false);
					worldIn.notifyNeighborsOfStateChange(pos.offset(facing.rotateYCCW()), this, false);
				}
			}
			else if (from == facing.getOpposite()) {
				if (worldIn.getRedstonePower(fromPos, facing) > 0) {
					worldIn.setBlockState(pos, state.withProperty(OUTPUT, false));
					worldIn.notifyNeighborsOfStateChange(pos, this, false);
					worldIn.notifyNeighborsOfStateChange(pos.offset(facing.rotateY()), this, false);
					worldIn.notifyNeighborsOfStateChange(pos.offset(facing.rotateYCCW()), this, false);
				}
			}
		}
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta % 4)).withProperty(OUTPUT, meta >= 4);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex() + (state.getValue(OUTPUT) ? 4 : 0);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, OUTPUT);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDING_BOX;
	}
	
	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}

}
