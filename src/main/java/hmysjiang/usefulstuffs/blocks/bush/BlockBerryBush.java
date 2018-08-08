package hmysjiang.usefulstuffs.blocks.bush;

import java.util.Random;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class BlockBerryBush extends Block implements IGrowable, IPlantable {
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 2);
	
	private EnumDyeColor color;

	public BlockBerryBush(EnumDyeColor color) {
		super(Material.PLANTS);
		setUnlocalizedName(Reference.ModBlocks.BERRYBUSH.getUnlocalizedName() + "_" + color.getDyeColorName());
		setRegistryName(Reference.ModBlocks.BERRYBUSH.getRegistryName().toString() + "_" + color.getDyeColorName());
		ModItems.itemblocks.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		setSoundType(SoundType.PLANT);
		setTickRandomly(true);
		setHardness(0.5F);
		setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
		
		this.color = color;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote && isMature(state)) {
			worldIn.setBlockState(pos, state.cycleProperty(AGE));
			
			//Spawn ItemEntity
			EntityItem entity = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(ModItems.berry, 1, this.color.getMetadata()));
			worldIn.spawnEntity(entity);
			entity.onCollideWithPlayer(playerIn);
			return true;
		}
		return false;
	}
	
	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		if (!worldIn.isRemote && isMature(worldIn.getBlockState(pos))) {
			worldIn.setBlockState(pos, worldIn.getBlockState(pos).cycleProperty(AGE));
			
			//Spawn ItemEntity
			EntityItem entity = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, new ItemStack(ModItems.berry, 1, this.color.getMetadata()));
			worldIn.spawnEntity(entity);
			entity.onCollideWithPlayer(playerIn);
		}
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
            super.updateTick(worldIn, pos, state, rand);

            if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
            if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(8) == 0)
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
		if (canGrow(worldIn, pos, state, false))
        {
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

}
