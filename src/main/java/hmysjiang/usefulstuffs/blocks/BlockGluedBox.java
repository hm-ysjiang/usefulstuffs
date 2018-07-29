package hmysjiang.usefulstuffs.blocks;

import java.util.Random;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.blocks.materials.BlockMaterials;
import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.tileentity.TileEntityGluedBox;
import hmysjiang.usefulstuffs.utils.helper.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGluedBox extends Block implements ITileEntityProvider {

	public BlockGluedBox() {
		super(new BlockMaterials.GluedBox());
		setUnlocalizedName(Reference.ModBlocks.GLUED_BOX.getUnlocalizedName());
		setRegistryName(Reference.ModBlocks.GLUED_BOX.getRegistryName());
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if (!worldIn.isRemote && stack.hasTagCompound()) {
			((TileEntityGluedBox)worldIn.getTileEntity(pos)).setContent(stack.getTagCompound());
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			ItemStack drop = new ItemStack(ModBlocks.glued_box, 1);
			TileEntity tile = worldIn.getTileEntity(pos);
			if (tile != null && tile instanceof TileEntityGluedBox) {
				NBTTagCompound compound = ((TileEntityGluedBox)tile).getContent();
				if (compound != null)
					drop.setTagCompound(compound);	
				worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, drop));
			}
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityGluedBox();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (playerIn.isSneaking() && heldItem == null) {
			TileEntityGluedBox tile = (TileEntityGluedBox) worldIn.getTileEntity(pos);
			if (tile != null) {
				if (!worldIn.isRemote) {
					NBTTagCompound compound = tile.getContent();
					if (compound != null && compound.hasKey("Name") && compound.hasKey("Meta")) {
						int meta = compound.getInteger("Meta");
						Block block = Block.getBlockFromName(compound.getString("Name"));
						if (block != null) {
							worldIn.removeTileEntity(pos);
							worldIn.setBlockState(pos, block.getStateFromMeta(meta));
							if (compound.hasKey("Tile")) {
								TileEntity tile2 = worldIn.getTileEntity(pos);
								if (tile2 != null) {
									NBTTagCompound tileData = compound.getCompoundTag("Tile");
									tileData.setInteger("x", pos.getX());
									tileData.setInteger("y", pos.getY());
									tileData.setInteger("z", pos.getZ());
									tile2.deserializeNBT(tileData);
									tile2.markDirty();
								}
							}	
						}
					}	
				}
				return true;
			}
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
	}
	
}
