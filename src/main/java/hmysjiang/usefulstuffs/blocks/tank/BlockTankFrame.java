package hmysjiang.usefulstuffs.blocks.tank;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.items.ItemTankContainer;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTankFrame extends Block implements ITileEntityProvider {
	
	public static final PropertyEnum<ItemTankContainer.TankTier> TIER = PropertyEnum.<ItemTankContainer.TankTier>create("tier", ItemTankContainer.TankTier.class);
	public static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625, 0, 0.0625, 0.9375, 1, 0.9375);
	
	public BlockTankFrame(boolean enabled) {
		super(Material.ROCK);
		setRegistryName(Reference.ModBlocks.TANK.getRegistryName());
		setUnlocalizedName(Reference.ModBlocks.TANK.getUnlocalizedName());
		if (enabled)
			ModItems.itemblocks.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		setSoundType(SoundType.METAL);
		setHardness(1.5F);
		setDefaultState(this.blockState.getBaseState().withProperty(TIER, ItemTankContainer.TankTier.NONE));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = playerIn.getHeldItem(hand);
		TileEntityTankFrame tile = (TileEntityTankFrame) worldIn.getTileEntity(pos);
		if (tile != null) {
			if (!worldIn.isRemote && stack.isEmpty() && playerIn.isSneaking() && state.getValue(TIER).availiable()) {
				ItemStack tank = tile.getTank();
				EntityItem item = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, tank.copy());
				worldIn.spawnEntity(item);
				item.onCollideWithPlayer(playerIn);
				tile.setTank(ItemStack.EMPTY);
				return true;
			}
			else if (stack.getItem() == ModItems.tank_container && !state.getValue(TIER).availiable()) {
				if (!worldIn.isRemote) {
					tile.setTank(stack.copy());
					stack.setCount(0);
				}
				worldIn.playSound(playerIn, pos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return true;
			}
			else if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
				if (!worldIn.isRemote)
					return FluidUtil.interactWithFluidHandler(playerIn, hand, worldIn, pos, facing);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, ((TileEntityTankFrame)worldIn.getTileEntity(pos)).getTank()));
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TIER);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TIER).getMeta();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TIER, ItemTankContainer.TankTier.byMeta(meta));
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTankFrame();
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDING_BOX;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		for (EnumFacing facing: EnumFacing.HORIZONTALS)
			if (face == facing)
				return BlockFaceShape.UNDEFINED;
		return super.getBlockFaceShape(worldIn, state, pos, face);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(I18n.format("usefulstuffs.tank.tooltip_1"));
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.tank.tooltip_2"));
	}

}
