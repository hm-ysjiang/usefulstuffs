package hmysjiang.usefulstuffs.blocks;

import hmysjiang.usefulstuffs.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRainDetector extends Block {
	
	public static final PropertyBool RAINING = PropertyBool.create("raining");
	private World worldObj;

	public BlockRainDetector() {
		super(Material.PISTON);
		setUnlocalizedName(Reference.ModBlocks.RAIN_DETECTOR.getUnlocalizedName());
		setRegistryName(Reference.ModBlocks.RAIN_DETECTOR.getRegistryName());
		this.setDefaultState(this.blockState.getBaseState().withProperty(RAINING, Boolean.valueOf(false)));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, RAINING);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		this.worldObj = worldIn;
		worldIn.setBlockState(pos, getDefaultState());
	}
	
	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		for (EnumFacing facing:EnumFacing.HORIZONTALS)
			if (side == facing)
				return true;
		return false;
	}

}
