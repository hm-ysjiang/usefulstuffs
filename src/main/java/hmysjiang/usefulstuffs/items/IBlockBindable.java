package hmysjiang.usefulstuffs.items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IBlockBindable {
	
	public NBTTagList getBoundBlockListFromNBT(ItemStack stack);
	
	public void addBlockToListInNBT(EntityPlayer player, BlockPos pos, ItemStack stack, EnumFacing side);
	
}
