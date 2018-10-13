package hmysjiang.usefulstuffs.enchantment;

import com.google.common.base.Predicate;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.items.ItemPackingGlue;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;

public class EnchantmentXL extends Enchantment {
	
	public static final EnumEnchantmentType GLUE = EnumHelper.addEnchantmentType(new ResourceLocation(Reference.MOD_ID, "enchantment_xl").toString(), new Predicate<Item>() {
		
		@Override
		public boolean apply(Item input) {
			return input instanceof ItemPackingGlue || input == Items.BOOK;
		}
	});
	public static final EnchantmentXL INSTANCE = new EnchantmentXL();

	public EnchantmentXL() {
		super(Rarity.RARE, GLUE, EntityEquipmentSlot.values());
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
