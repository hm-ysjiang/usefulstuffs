package hmysjiang.usefulstuffs.blocks;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.tileentity.TileEntityWell;
import hmysjiang.usefulstuffs.tileentity.capability.FilteredItemStackHandler;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
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
		tooltip.add(I18n.format("usefulstuffs.well.tooltip_1"));
		tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.well.tooltip_2", new TileEntityWell().getTransferRate()));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			if (heldItem == null) {
				if (playerIn.isSneaking()) {
					TileEntityWell tile = (TileEntityWell) worldIn.getTileEntity(pos);
					IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
					if (handler.extractItem(0, 1, true) != null) {
						EntityItem entity = new EntityItem(worldIn, pos.getX()+0.5, pos.getY()+1, pos.getZ()+0.5, handler.extractItem(0, 1, false));
						worldIn.spawnEntityInWorld(entity);
					}
					return true;					
				}

			}
			else if (heldItem.isItemEqual(new ItemStack(ModItems.waterfilter))) {
				TileEntityWell tile = (TileEntityWell) worldIn.getTileEntity(pos);
				FilteredItemStackHandler handler = (FilteredItemStackHandler) tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
				if (handler.insertItem(0, heldItem.copy(), true) == null) {
					handler.insertItem(0, heldItem.copy(), false);
					playerIn.inventory.deleteStack(heldItem);
				}
				return true;
			}
			return false;
		}
		return false;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		IItemHandler handler = ((TileEntityWell)worldIn.getTileEntity(pos)).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
		for (int i = 0 ; i<handler.getSlots() ; i++) 
			if (handler.getStackInSlot(i) != null)
				worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX()+0.5, pos.getY()+1, pos.getZ()+0.5, handler.getStackInSlot(i)));
		super.breakBlock(worldIn, pos, state);
	}

}
