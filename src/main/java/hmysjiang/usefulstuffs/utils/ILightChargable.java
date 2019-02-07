package hmysjiang.usefulstuffs.utils;

import hmysjiang.usefulstuffs.enchantment.EnchantmentMoonLight;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public interface ILightChargable {
	
	static final double DOUBLE_PI = Math.PI * 2;
	static final double HALF_PI = Math.PI / 2;
	
	default void charge(World world, ItemStack stack, BlockPos pos) {
		if (world.isRemote)	return;
		if (!world.canBlockSeeSky(pos)) return;
		double angY = (double) world.getCelestialAngle(0) * DOUBLE_PI + HALF_PI;
		if (angY >= DOUBLE_PI)
			angY -= DOUBLE_PI;
		int charge = getChargeAmount(angY, EnchantmentHelper.getEnchantmentLevel(EnchantmentMoonLight.INSTANCE, stack) > 0, ((double)(world.getRainStrength(0) * 5.0F) / 16.0D), ((double)(world.getThunderStrength(0) * 5.0F) / 16.0D));
		if (stack.isItemDamaged()) {
			stack.setItemDamage(stack.getItemDamage() > charge ? stack.getItemDamage() - charge : 0);
		}
	}
	
	default int getChargeAmount(double radian, boolean moonlight, double rainDecre, double thunderIncre) {
		if (radian < Math.PI && radian > 0) 
			return (int) (MathHelper.sin((float) radian) * 4 * (1.0D - rainDecre + thunderIncre) + 1);
		if (moonlight)
			return (int) (-MathHelper.sin((float) radian) * 2 * (1.0D - rainDecre + thunderIncre) + 1);
		return 0;
	}
	
}
