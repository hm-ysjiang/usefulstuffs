package hmysjiang.usefulstuffs.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IItemBindable {
	
	public NBTTagList getBoundBlockListFromNBT(ItemStack stack);
	
	public void addBlockToListInNBT(World world, BlockPos pos, ItemStack stack, EnumFacing side);
	
}
