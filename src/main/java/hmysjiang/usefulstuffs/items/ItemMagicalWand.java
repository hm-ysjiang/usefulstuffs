package hmysjiang.usefulstuffs.items;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.miscs.ExplosionUnharmful;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ItemMagicalWand extends Item {
	
	public ItemMagicalWand() {
		setUnlocalizedName(Reference.ModItems.MAGICAL_WAND.getUnlocalizedName());
		setRegistryName(Reference.ModItems.MAGICAL_WAND.getRegistryName());
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		new ExplosionUnharmful(worldIn, 3.0F, pos.getX(), pos.getY(), pos.getZ()).explode();
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
}