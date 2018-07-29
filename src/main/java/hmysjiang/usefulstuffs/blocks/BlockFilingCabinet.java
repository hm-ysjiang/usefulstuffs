package hmysjiang.usefulstuffs.blocks;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.blocks.materials.BlockMaterials;
import hmysjiang.usefulstuffs.client.gui.GUIHandler;
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
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockFilingCabinet extends BlockHorizontal implements ITileEntityProvider {
	
	public BlockFilingCabinet() {
		super(new BlockMaterials.FilingCabinet());
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
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if (tile != null && tile instanceof TileEntityFilingCabinet) {
			if (!worldIn.isRemote) {
				playerIn.openGui(UsefulStuffs.instance, GUIHandler.GUI_FILING_CABINET_1, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		IItemHandler handler = ((TileEntityFilingCabinet)worldIn.getTileEntity(pos)).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for (int i = 0 ; i<handler.getSlots() ; i++)
			if (handler.getStackInSlot(i) != null)
				worldIn.spawnEntity(new EntityItem(worldIn, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, handler.getStackInSlot(i)));
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
		tooltip.add(TextFormatting.GOLD + I18n.format("usefulstuffs.filing_cabinet.tooltip_1"));
		tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.filing_cabinet.tooltip_2"));
	}
	
}