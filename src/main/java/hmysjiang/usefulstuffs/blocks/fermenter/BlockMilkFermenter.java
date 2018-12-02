package hmysjiang.usefulstuffs.blocks.fermenter;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.blocks.BlockMaterials;
import hmysjiang.usefulstuffs.client.gui.GuiHandler;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.utils.helper.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

public class BlockMilkFermenter extends Block implements ITileEntityProvider {
	
	public BlockMilkFermenter() {
		super(Material.CACTUS);
		setUnlocalizedName(Reference.ModBlocks.MILK_FERMENTER.getUnlocalizedName());
		setRegistryName(Reference.ModBlocks.MILK_FERMENTER.getRegistryName());
		ModItems.itemblocks.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		setSoundType(SoundType.PLANT);
		setHardness(0.5F);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMilkFermenter();
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (worldIn.isRemote) return;
		if (worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos) instanceof TileEntityMilkFermenter) {
			WorldHelper.spawnItemsInHandler(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((TileEntityMilkFermenter)worldIn.getTileEntity(pos)).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote)
			playerIn.openGui(UsefulStuffs.instance, GuiHandler.GUI_FERMENTER, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("usefulstuffs.milk_fermenter.tooltip"));
	}
	
}
