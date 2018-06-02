package hmysjiang.usefulstuffs.blocks;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.tileentity.TileEntityFilingCabinet;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockFilingCabinet extends BlockHorizontal implements ITileEntityProvider {
	
	public BlockFilingCabinet() {
		super(Material.WOOD);
		setUnlocalizedName(Reference.ModBlocks.FILING_CABINET.getUnlocalizedName());
		setRegistryName(Reference.ModBlocks.FILING_CABINET.getRegistryName());
		setHardness(5.0F);
		setSoundType(SoundType.WOOD);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFilingCabinet();
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityFilingCabinet();
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		IItemHandler handler = ((TileEntityFilingCabinet)worldIn.getTileEntity(pos)).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for (int i = 0 ; i<handler.getSlots() ; i++)
			if (handler.getStackInSlot(i) != null)
				worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, handler.getStackInSlot(i)));
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex();
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.wip.tooltip"));
		tooltip.add(TextFormatting.GOLD + I18n.format("usefulstuffs.filing_cabinet.tooltip_1"));
		tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.filing_cabinet.tooltip_2"));
		tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.filing_cabinet.tooltip_3"));
	}
	
}