package hmysjiang.usefulstuffs.blocks.bush;

import java.util.Random;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class BlockBerryBush extends Block implements IGrowable, IPlantable {
	public static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.125, 0, 0.125, 0.875, 0.8125, 0.875);
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 2);
	public static int growthChance = -1;
	
	private EnumBerryColor color;

	public BlockBerryBush(EnumBerryColor color) {
		super(Material.PLANTS);
		setUnlocalizedName(Reference.ModBlocks.BERRYBUSH.getUnlocalizedName() + "_" + color.getDyeColorName());
		setRegistryName(Reference.ModBlocks.BERRYBUSH.getRegistryName().toString() + "_" + color.getDyeColorName());
		ModItems.itemblocks.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		setSoundType(SoundType.PLANT);
		setTickRandomly(true);
		setHardness(0.5F);
		setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
		
		this.color = color;
		
		if (growthChance == -1)
			growthChance = ConfigManager.bushGrowthChance;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			if (!(playerIn.getHeldItem(hand).getItem() instanceof ItemBlock) || !(((ItemBlock)playerIn.getHeldItem(hand).getItem()).getBlock() instanceof BlockBerryBush)) {
				if (isMature(state)) {
					worldIn.setBlockState(pos, state.withProperty(AGE, 1));
					EntityItem entity = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(ModItems.berry, 1, this.color.getMetadata()));
					worldIn.spawnEntity(entity);
					entity.onCollideWithPlayer(playerIn);
					return true;	
				}	
			}
		}
		return false;
	}
	
	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		if (!worldIn.isRemote && isMature(worldIn.getBlockState(pos))) {
			worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(AGE, 1));
			EntityItem entity = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(ModItems.berry, 1, this.color.getMetadata()));
			worldIn.spawnEntity(entity);
			entity.onCollideWithPlayer(playerIn);
		}
	}
	
	public IBlockState getRandomSpawnState(Random random) {
		return this.getDefaultState().withProperty(AGE, random.nextInt(2) + 1);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, AGE);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(AGE).intValue();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(AGE, meta);
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote)
		{
			if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
			if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(growthChance) == 0)
			{
				this.grow(worldIn, rand, pos, state);
			}
		}
	}
	
	public boolean isMature(IBlockState state) {
		return state.getValue(AGE).intValue() >= 2;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		if (canGrow(worldIn, pos, state, false)) {
			worldIn.setBlockState(pos, state.cycleProperty(AGE));
		}
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return !isMature(state);
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Plains;
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
		return world.getBlockState(pos).getBlock() != this ? getDefaultState() : world.getBlockState(pos);
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
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
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
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
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		pos = pos.down();
		return worldIn.getBlockState(pos).getBlock() instanceof BlockBerryBush || worldIn.getBlockState(pos).getBlock() == Blocks.STONE || worldIn.getBlockState(pos).getBlock() == Blocks.GRASS || worldIn.getBlockState(pos).getBlock() == Blocks.DIRT;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!canPlaceBlockAt(worldIn, pos)) {
			this.dropBlockAsItem(worldIn, pos, state.withProperty(AGE, 0), 0);
			worldIn.setBlockToAir(pos);
		}
	}
	
	@Override
	public boolean canSpawnInBlock() {
		return true;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (worldIn.isRainingAt(pos.up()) && !worldIn.getBlockState(pos.down()).isTopSolid() && rand.nextInt(12) == 1) {
			double d0 = (double)((float)pos.getX() + rand.nextFloat());
			double d1 = (double)pos.getY() - 0.05D;
			double d2 = (double)((float)pos.getZ() + rand.nextFloat());
			worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}

}
