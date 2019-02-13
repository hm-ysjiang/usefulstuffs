package hmysjiang.usefulstuffs.blocks.pressureplates;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.Reference.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockInvertedPressurePlate extends BlockPressurePlate {

	public BlockInvertedPressurePlate(Material materialIn, Sensitivity sensitivityIn, ModBlocks blockEnum, boolean enabled) {
		super(materialIn, sensitivityIn);
		setUnlocalizedName(blockEnum.getUnlocalizedName());
		setRegistryName(blockEnum.getRegistryName());
		if (enabled)
			ModItems.itemblocks.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		setHardness(0.5F);
		setSoundType(materialIn == Material.ROCK ? SoundType.STONE : SoundType.WOOD);
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return 15 - super.getWeakPower(blockState, blockAccess, pos, side);
	}
	
	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return 15 - super.getStrongPower(blockState, blockAccess, pos, side);
	}

}
