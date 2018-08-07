package hmysjiang.usefulstuffs.blocks.gluedbox;

import java.util.List;
import java.util.Random;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.blocks.BlockMaterials;
import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BlockGluedBox extends Block implements ITileEntityProvider {

	public BlockGluedBox() {
		super(new BlockMaterials.GluedBox());
		setUnlocalizedName(Reference.ModBlocks.GLUED_BOX.getUnlocalizedName());
		setRegistryName(Reference.ModBlocks.GLUED_BOX.getRegistryName());
		ModItems.itemblocks.add(new ItemBlock(this).setRegistryName(getRegistryName()));
		setSoundType(SoundType.SLIME);
		setDefaultSlipperiness(0.8F);
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
				worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, drop));
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
			EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (playerIn.isSneaking() && playerIn.getHeldItemMainhand().isEmpty()) {
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
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, side, hitX, hitY, hitZ);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Name")) {
			Block block = Block.getBlockFromName(stack.getTagCompound().getString("Name"));
			if (block != null) {
				tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.glued_box.tooltip_3", block.getLocalizedName()));
				return;
			}
		}
		tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.glued_box.tooltip_1"));
		tooltip.add(TextFormatting.GOLD + I18n.format("usefulstuffs.glued_box.tooltip_2"));
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
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
        if (entityIn.isSneaking())
        {
            super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
        }
        else
        {
            entityIn.fall(fallDistance, 0.0F);
        }
    }
	
	@Override
	public void onLanded(World worldIn, Entity entityIn)
    {
        if (entityIn.isSneaking())
        {
            super.onLanded(worldIn, entityIn);
        }
        else if (entityIn.motionY < 0.0D)
        {
            entityIn.motionY = -entityIn.motionY;

            if (!(entityIn instanceof EntityLivingBase))
            {
                entityIn.motionY *= 0.8D;
            }
        }
    }
	
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        if (Math.abs(entityIn.motionY) < 0.1D && !entityIn.isSneaking())
        {
            double d0 = 0.4D + Math.abs(entityIn.motionY) * 0.2D;
            entityIn.motionX *= d0;
            entityIn.motionZ *= d0;
        }

        super.onEntityWalk(worldIn, pos, entityIn);
    }
	
}
