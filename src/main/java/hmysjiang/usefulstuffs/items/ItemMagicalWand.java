package hmysjiang.usefulstuffs.items;

import hmysjiang.usefulstuffs.Reference;
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
	
	/***
	 * Item for testing
	 */
	public ItemMagicalWand() {
		setUnlocalizedName(Reference.ModItems.MAGICAL_WAND.getUnlocalizedName());
		setRegistryName(Reference.ModItems.MAGICAL_WAND.getRegistryName());
	}
	
}