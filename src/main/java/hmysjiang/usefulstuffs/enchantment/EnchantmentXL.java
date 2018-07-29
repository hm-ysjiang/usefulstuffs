package hmysjiang.usefulstuffs.enchantment;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.items.ItemPackingGlue;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentXL extends Enchantment {
	
	public static final EnchantmentXL instance = new EnchantmentXL();

	public EnchantmentXL() {
		super(Rarity.COMMON, EnumEnchantmentType.BREAKABLE, EntityEquipmentSlot.values());
		setRegistryName(Reference.MOD_ID, "extra_large");
		setName("usefulstuffs.xl.name");
	}
	
	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 1 + (enchantmentLevel - 1) + 10;
	}
	
	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return getMinEnchantability(enchantmentLevel) + 50;
	}
	
	@Override
	public int getMaxLevel() {
		return 3;
	}
	
	@Override
	public boolean canApply(ItemStack stack) {
		return stack.getItem() instanceof ItemPackingGlue;
	}

}
