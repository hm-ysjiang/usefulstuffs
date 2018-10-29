package hmysjiang.usefulstuffs.utils;

import hmysjiang.usefulstuffs.enchantment.EnchantmentMoonLight;
import hmysjiang.usefulstuffs.items.ItemLightBow;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public interface ILightChargable {
	
	default void charge(World world, ItemStack stack, BlockPos pos) {
		if (world.isRemote)	return;
		if (!world.canBlockSeeSky(pos) || world.isRaining()) return;
		int time = (int) world.getWorldTime();
		int charge = getChargeAmount(time, stack.getItem() instanceof ItemLightBow && EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0, EnchantmentHelper.getEnchantmentLevel(EnchantmentMoonLight.INSTANCE, stack) > 0);
		if (stack.isItemDamaged()) {
			stack.setItemDamage(stack.getItemDamage() > charge ? stack.getItemDamage() - charge : 0);
		}
	}
	
	default int getChargeAmount(int time, boolean infinity, boolean moonlight) {
		if (infinity) {
			return 4;
		}
		if (time <= 12000) {
			int dif = MathHelper.abs(6000 - time);
			int charge =  (int) (MathHelper.cos((float) (Math.PI * dif / 12000)) * 4 + 1);
			return charge;
		}
		if (moonlight) {
			int aug_time = time - 12000;
			int dif = MathHelper.abs(6000 - aug_time);
			int charge =  (int) (MathHelper.cos((float) (Math.PI * dif / 12000)) * 2 + 1);
			return charge;
		}
		return 0;
	}
	
}
