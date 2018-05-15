package hmysjiang.usefulstuffs.items;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class ItemWaterBlackListFilter extends Item implements IBlockBindable {
	
	public static final String KEY_BLOCKS = "Blocks";
	
	public ItemWaterBlackListFilter() {
		setUnlocalizedName(Reference.ModItems.WATERFILTER.getUnlocalizedName());
		setRegistryName(Reference.ModItems.WATERFILTER.getRegistryName());
	}

	@Override
	public NBTTagList getBoundBlockListFromNBT(ItemStack stack) {
		return (stack.hasTagCompound() && stack.getTagCompound().hasKey(KEY_BLOCKS)) ? stack.getTagCompound().getTagList(KEY_BLOCKS, Constants.NBT.TAG_COMPOUND) : new NBTTagList() ;
	}

	@Override
	public void addBlockToListInNBT(EntityPlayer player, BlockPos pos, ItemStack stack, EnumFacing side) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(KEY_BLOCKS)) {
			NBTTagList list = stack.getTagCompound().getTagList(KEY_BLOCKS, Constants.NBT.TAG_COMPOUND);
			NBTTagIntArray data = new NBTTagIntArray(new int[] {player.dimension, pos.getX(), pos.getY(), pos.getZ(), side.getIndex()});
			list.appendTag(data);
			stack.getTagCompound().setTag(KEY_BLOCKS, list);
		}
		else {
			NBTTagCompound compound = new NBTTagCompound();
			NBTTagList list = new NBTTagList();
			NBTTagIntArray data = new NBTTagIntArray(new int[] {player.dimension, pos.getX(), pos.getY(), pos.getZ(), side.getIndex()});
			list.appendTag(data);
			compound.setTag(KEY_BLOCKS, list);
			stack.setTagCompound(compound);
		}
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (playerIn.isSneaking() && worldIn.getBlockState(pos) == ModBlocks.well.getDefaultState()) {
			addBlockToListInNBT(playerIn, pos, stack, facing);
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
}
