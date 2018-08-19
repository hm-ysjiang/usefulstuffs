package hmysjiang.usefulstuffs.blocks.well;

import java.util.List;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockWell extends Block implements ITileEntityProvider {

	public static int rate;
	public static int range;
	
	public BlockWell() {
		super(Material.ROCK);
		setUnlocalizedName(Reference.ModBlocks.WELL.getUnlocalizedName());
		setRegistryName(Reference.ModBlocks.WELL.getRegistryName());
		ModItems.itemblocks.add(new ItemBlock(this).setRegistryName(getRegistryName()));
		setHardness(5.0F);
		
		rate = ConfigManager.wellTransRate;
		range = ConfigManager.wellTransRange;
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
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, worldIn, tooltip, advanced);
		tooltip.add(I18n.format("usefulstuffs.well.tooltip_1", (range * 2 + 1), (range * 2 + 1)));
		tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.well.tooltip_2", rate));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			ItemStack heldItem = playerIn.getHeldItem(hand);
			if (heldItem.isEmpty()) {
				if (playerIn.isSneaking()) {
					TileEntityWell tile = (TileEntityWell) worldIn.getTileEntity(pos);
					IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
					if (!handler.extractItem(0, 1, true).isEmpty()) {
						EntityItem entity = new EntityItem(worldIn, pos.getX()+0.5, pos.getY()+1, pos.getZ()+0.5, handler.extractItem(0, 1, false));
						worldIn.spawnEntity(entity);
					}
					return true;					
				}

			}
			else if (heldItem.isItemEqualIgnoreDurability(new ItemStack(ModItems.water_blacklist))) {
				TileEntityWell tile = (TileEntityWell) worldIn.getTileEntity(pos);
				TileEntityWell.Handler handler = (TileEntityWell.Handler) tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
				if (handler.insertItem(0, heldItem.copy(), true).isEmpty()) {
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
		if (worldIn.getTileEntity(pos) != null) {
			IItemHandler handler = ((TileEntityWell)worldIn.getTileEntity(pos)).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
			for (int i = 0 ; i<handler.getSlots() ; i++) 
				if (handler.getStackInSlot(i) != null)
					worldIn.spawnEntity(new EntityItem(worldIn, pos.getX()+0.5, pos.getY()+1, pos.getZ()+0.5, handler.getStackInSlot(i)));
		}
		super.breakBlock(worldIn, pos, state);
	}

}
