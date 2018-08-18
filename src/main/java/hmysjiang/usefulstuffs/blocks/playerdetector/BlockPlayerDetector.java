package hmysjiang.usefulstuffs.blocks.playerdetector;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.blocks.BlockMaterials;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.utils.LogHelper;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPlayerDetector extends BlockHorizontal implements ITileEntityProvider {
	
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	public static AxisAlignedBB getBoundingBoxFromFacing(EnumFacing facing) {
		switch (facing) {
		case NORTH:
			return new AxisAlignedBB(0.0625, 0, 0, 0.0625 * 15, 0.0625 * 2, 0.0625 * 2);
		case SOUTH:
			return new AxisAlignedBB(0.0625, 0, 0.0625 * 14, 0.0625 * 15, 0.0625 * 2, 1);
		case EAST:
			return new AxisAlignedBB(0.0625 * 14, 0, 0.0625, 1, 0.0625 * 2, 0.0625 * 15);
		case WEST:
			return new AxisAlignedBB(0, 0, 0.0625, 0.0625 * 2, 0.0625 * 2, 0.0625 * 15);
		default: 
			return new AxisAlignedBB(0.0625, 0, 0, 0.0625 * 15, 0.0625 * 2, 0.0625 * 2);
		}
	}

	public BlockPlayerDetector() {
		super(new BlockMaterials.Circuit());
		setUnlocalizedName(Reference.ModBlocks.PLAYER_DETECTOR.getUnlocalizedName());
		setRegistryName(Reference.ModBlocks.PLAYER_DETECTOR.getRegistryName());
		ModItems.itemblocks.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		setSoundType(SoundType.METAL);
		setHardness(1.5F);
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
		return side == EnumFacing.DOWN || side == state.getValue(FACING);
	}
	
	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		if (!((Boolean)blockState.getValue(POWERED)).booleanValue())
			return 0;
		return side == blockState.getValue(FACING).getOpposite() || side == EnumFacing.DOWN ? 15 : 0;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return ((Boolean)blockState.getValue(POWERED)).booleanValue() ? 15 : 0;
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()).withProperty(POWERED, Boolean.valueOf(false));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, POWERED);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta % 4)).withProperty(POWERED, Boolean.valueOf(meta >= 4));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex() + (((Boolean)state.getValue(POWERED)).booleanValue() ? 4 : 0);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return getBoundingBoxFromFacing(state.getValue(FACING));
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return getBoundingBox(blockState, worldIn, pos);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityPlayerDetector();
	}
	
	public void updateState(World world, BlockPos pos, boolean powered) {
		if (world.getBlockState(pos).getBlock() == this) {	//Prevent TileEntity from tick this after block is broken
			boolean flag = powered ^ ((Boolean)world.getBlockState(pos).getValue(POWERED)).booleanValue();
			world.setBlockState(pos, world.getBlockState(pos).withProperty(POWERED, Boolean.valueOf(powered)));
			if (flag) 
				notifyNeighbors(world, pos, world.getBlockState(pos).getValue(FACING));	
		}
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (((Boolean)state.getValue(POWERED)).booleanValue())
		{
			this.notifyNeighbors(worldIn, pos, state.getValue(FACING));
		}

		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	
	private void notifyNeighbors(World world, BlockPos pos, EnumFacing facing) {
		world.notifyNeighborsOfStateChange(pos, this, false);
		world.notifyNeighborsOfStateChange(pos.offset(facing), this, false);
		world.notifyNeighborsOfStateChange(pos.offset(EnumFacing.DOWN), this, false);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(I18n.format("usefulstuffs.player_detector.tooltip"));
	}

}
