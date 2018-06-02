package hmysjiang.usefulstuffs.blocks;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.tileentity.TileEntityLantern;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//unused
public class BlockLantern extends Block implements ITileEntityProvider {

	public BlockLantern() {
		super(new Material(MapColor.GOLD));
		setUnlocalizedName(Reference.ModBlocks.LANTERN.getUnlocalizedName());
		setRegistryName(Reference.ModBlocks.LANTERN.getRegistryName());
		setSoundType(SoundType.GLASS);
		setHardness(2.0F);
		setLightLevel(1.0F);
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		// TODO Auto-generated method stub
		super.onBlockAdded(worldIn, pos, state);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityLantern();
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityLantern();
	}

}
