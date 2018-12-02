package hmysjiang.usefulstuffs.blocks.universaluser;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.blocks.BlockMaterials;
import hmysjiang.usefulstuffs.client.gui.GuiHandler;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUniversalUser extends BlockDirectional implements ITileEntityProvider {

	public BlockUniversalUser() {
		super(Material.ROCK);
		setUnlocalizedName(Reference.ModBlocks.UNIVERSAL_USER.getUnlocalizedName());
		setRegistryName(Reference.ModBlocks.UNIVERSAL_USER.getRegistryName());
		ModItems.itemblocks.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setHardness(1.5F);
		setSoundType(SoundType.METAL);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote)
			playerIn.openGui(UsefulStuffs.instance, GuiHandler.GUI_USER, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityUniversalUser();
	}

}
