package hmysjiang.usefulstuffs.blocks.playerdetector;

import java.util.List;
import java.util.Random;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.blocks.BlockMaterials;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.utils.helper.LogHelper;
import hmysjiang.usefulstuffs.utils.helper.WorldHelper;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPlayerDetector extends BlockHorizontal implements ITileEntityProvider {
	
	public static int range;
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	public static final PropertyBool UP = PropertyBool.create("upside");
	public static AxisAlignedBB getBoundingBoxFromFacing(EnumFacing facing, boolean up) {
		switch (facing) {
		case NORTH:
			if (up)
				return new AxisAlignedBB(0.0625, 0.0625 * 14, 0, 0.0625 * 15, 1, 0.0625 * 2);
			return new AxisAlignedBB(0.0625, 0, 0, 0.0625 * 15, 0.0625 * 2, 0.0625 * 2);
		case SOUTH:
			if (up)
				return new AxisAlignedBB(0.0625, 0.0625 * 14, 0.0625 * 14, 0.0625 * 15, 1, 1);
			return new AxisAlignedBB(0.0625, 0, 0.0625 * 14, 0.0625 * 15, 0.0625 * 2, 1);
		case EAST:
			if (up)
				return new AxisAlignedBB(0.0625 * 14, 0.0625 * 14, 0.0625, 1, 1, 0.0625 * 15);
			return new AxisAlignedBB(0.0625 * 14, 0, 0.0625, 1, 0.0625 * 2, 0.0625 * 15);
		case WEST:
			if (up)
				return new AxisAlignedBB(0, 0.0625 * 14, 0.0625, 0.0625 * 2, 1, 0.0625 * 15);
			return new AxisAlignedBB(0, 0, 0.0625, 0.0625 * 2, 0.0625 * 2, 0.0625 * 15);
		default: 
			return new AxisAlignedBB(0.0625, 0, 0, 0.0625 * 15, 0.0625 * 2, 0.0625 * 2);
		}
	}

	public BlockPlayerDetector() {
		super(Material.ROCK);
		setUnlocalizedName(Reference.ModBlocks.PLAYER_DETECTOR.getUnlocalizedName());
		setRegistryName(Reference.ModBlocks.PLAYER_DETECTOR.getRegistryName());
		ModItems.itemblocks.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		setSoundType(SoundType.METAL);
		setHardness(0.8F);
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH).withProperty(POWERED, false).withProperty(UP, false));
		
		range = ConfigManager.playerDetectorRange;
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
		return side == state.getValue(FACING) || side == (state.getValue(UP) ? EnumFacing.UP : EnumFacing.DOWN);
	}
	
	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		if (!((Boolean)blockState.getValue(POWERED)).booleanValue())
			return 0;
		return side == blockState.getValue(FACING).getOpposite() || side == (blockState.getValue(UP) ? EnumFacing.UP : EnumFacing.DOWN) ? 15 : 0;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return ((Boolean)blockState.getValue(POWERED)).booleanValue() ? 15 : 0;
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		if (facing == EnumFacing.DOWN)
			return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()).withProperty(POWERED, Boolean.valueOf(false)).withProperty(UP, true);
		if (facing == EnumFacing.UP)
			return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()).withProperty(POWERED, Boolean.valueOf(false)).withProperty(UP, false);
		if (hitY < 0.5)
			return this.getDefaultState().withProperty(FACING, facing.getOpposite()).withProperty(POWERED, Boolean.valueOf(false)).withProperty(UP, false);
		return this.getDefaultState().withProperty(FACING, facing.getOpposite()).withProperty(POWERED, Boolean.valueOf(false)).withProperty(UP, true);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, POWERED, UP);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta % 4)).withProperty(POWERED, Boolean.valueOf(meta % 8 >= 4)).withProperty(UP, Boolean.valueOf(meta >= 8));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex() + (((Boolean)state.getValue(POWERED)).booleanValue() ? 4 : 0) + (((Boolean)state.getValue(UP)).booleanValue() ? 8 : 0);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return getBoundingBoxFromFacing(state.getValue(FACING), state.getValue(UP));
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return getBoundingBox(blockState, worldIn, pos);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityPlayerDetector();
	}
	
	public void updateState(World world, BlockPos pos, boolean allowOff) {
		if (world.getBlockState(pos).getBlock() == this) {	//Prevent TileEntity from tick this after block is broken
			AxisAlignedBB bb = new AxisAlignedBB(pos).grow(range);
			EnumFacing facing = world.getBlockState(pos).getValue(FACING);
			boolean powered = false;
			if (ConfigManager.onlyDetectOneSide) 
				for (EntityPlayer player: world.getEntitiesWithinAABB(EntityPlayer.class, bb)) {
					if (WorldHelper.isRelationCorrect(pos, player.getPosition(), facing.getOpposite(), true)) {
						powered = true;
						break;
					}
				}
			else 
				powered = world.getEntitiesWithinAABB(EntityPlayer.class, bb).size() > 0;
			boolean flag = powered ^ ((Boolean)world.getBlockState(pos).getValue(POWERED)).booleanValue();
			if (powered)
				world.setBlockState(pos, world.getBlockState(pos).withProperty(POWERED, Boolean.valueOf(true)));
			else if (allowOff)
				world.setBlockState(pos, world.getBlockState(pos).withProperty(POWERED, Boolean.valueOf(false)));
			if (flag) {
				notifyNeighbors(world, pos, facing);
				world.scheduleUpdate(pos, this, 20);
			}
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
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(I18n.format("usefulstuffs.player_detector.tooltip", (range * 2 + 1), (range * 2 + 1), (range * 2 + 1)));
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) 
			this.updateState(worldIn, pos, true);
	}

}
