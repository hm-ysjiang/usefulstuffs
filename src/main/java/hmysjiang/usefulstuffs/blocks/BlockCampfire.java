package hmysjiang.usefulstuffs.blocks;

import java.util.List;
import java.util.Random;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.blocks.materials.BlockMaterials;
import hmysjiang.usefulstuffs.tileentity.TileEntityCampfire;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCampfire extends Block implements ITileEntityProvider {
	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625, 0, 0.0625, 0.0625*15, 0.0625*4, 0.0625*15);
	private Random rnd = new Random();

	public BlockCampfire() {
		super(new BlockMaterials.Campfire());
		setUnlocalizedName(Reference.ModBlocks.CAMPFIRE.getUnlocalizedName());
		setRegistryName(Reference.ModBlocks.CAMPFIRE.getRegistryName());
		setLightLevel(1.0F);
		setSoundType(SoundType.WOOD);
		setHardness(0.5F);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			if (playerIn.isSneaking()) 
				playerIn.sendMessage(new TextComponentString("Radius :"+((TileEntityCampfire)worldIn.getTileEntity(pos)).getBuffRadius()));
		}
		return true;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.STICK, rnd.nextInt(2)+2)));
		super.breakBlock(worldIn, pos, state);
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
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDING_BOX;
	}
	
	@Override 
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCampfire();
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.campfire.tooltip_1"));
		tooltip.add(TextFormatting.YELLOW + I18n.format("usefulstuffs.campfire.tooltip_2"));
	}

}
