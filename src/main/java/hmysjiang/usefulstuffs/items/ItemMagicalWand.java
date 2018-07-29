package hmysjiang.usefulstuffs.items;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.blocks.BlockTFlipFlop;
import hmysjiang.usefulstuffs.tileentity.TileEntityTFlipFlop;
import hmysjiang.usefulstuffs.utils.helper.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMagicalWand extends Item {
	
	/***
	 * Item for testing
	 */
	public ItemMagicalWand() {
		setUnlocalizedName(Reference.ModItems.MAGICAL_WAND.getUnlocalizedName());
		setRegistryName(Reference.ModItems.MAGICAL_WAND.getRegistryName());
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			LogHelper.info("isBlockIndirectlyGettingPowered: " + worldIn.isBlockIndirectlyGettingPowered(pos));
			LogHelper.info("isBlockPowered: " + worldIn.isBlockPowered(pos));
			LogHelper.info("Hit on the " + facing.getName() + " side of the block");
			LogHelper.info("isSidePowered: " + worldIn.isSidePowered(pos, facing));
		}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
}