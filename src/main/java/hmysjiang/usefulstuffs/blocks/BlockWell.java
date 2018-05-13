package hmysjiang.usefulstuffs.blocks;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.tileentity.TileEntityWell;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;

public class BlockWell extends Block implements ITileEntityProvider {

	public BlockWell() {
		super(Material.ROCK);
		setUnlocalizedName(Reference.ModBlocks.WELL.getUnlocalizedName());
		setRegistryName(Reference.ModBlocks.WELL.getRegistryName());
		setHardness(5.0F);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityWell();
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityWell();
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		tooltip.add(I18n.format("usefulstuffs.well.tooltip_1", new TileEntityWell().getTransferRate()));
		tooltip.add(I18n.format("usefulstuffs.well.tooltip_2"));
	}
	
	@Override
	public String getHarvestTool(IBlockState state) {
		return null;
	}

}
