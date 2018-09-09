package hmysjiang.usefulstuffs.blocks.raindetector;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRainDetector extends Block implements ITileEntityProvider {
	public static final PropertyBool RAINING = PropertyBool.create("raining");

	public BlockRainDetector() {
		super(new Material(MapColor.STONE));
		setUnlocalizedName(Reference.ModBlocks.RAIN_DETECTOR.getUnlocalizedName());
		setRegistryName(Reference.ModBlocks.RAIN_DETECTOR.getRegistryName());
		ModItems.itemblocks.add(new ItemBlock(this).setRegistryName(getRegistryName()));
		this.setDefaultState(this.blockState.getBaseState().withProperty(RAINING, Boolean.valueOf(false)));
		this.setHardness(1.5F);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, RAINING);
	}
	
	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		if (side == EnumFacing.UP)
			return false;
		return true;
	}
	
	public void updateState(World world, BlockPos pos) {
		if (world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.up())).size() > 0) {
			if (!world.getBlockState(pos).getValue(RAINING))
				world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
			world.setBlockState(pos, getDefaultState().withProperty(RAINING, true));
			return;
		}
		if (!world.canBlockSeeSky(pos)) {
			if (world.getBlockState(pos).getValue(RAINING))
				world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.6F);
			world.setBlockState(pos, this.getDefaultState());
			return;
		}
		else {
			if (world.getBlockState(pos).getValue(RAINING)) {
				if (!world.isRaining()) {
					world.setBlockState(pos, this.getDefaultState());
					world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.6F);
				}
			}
			else {
				if (world.isRaining()) {
					world.setBlockState(pos, this.getDefaultState().withProperty(RAINING, Boolean.valueOf(true)));
					world.playSound(null, pos, SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
				}
			}
		}
	}
	
	@Override
	public IBlockState getStateFromMeta(int raining) {
		return this.getDefaultState().withProperty(RAINING, raining == 1);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(RAINING) ? 1 : 0 ;
	}
	
	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockState.getValue(RAINING) ? 15 : 0;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityRainDetector();
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
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}

}
