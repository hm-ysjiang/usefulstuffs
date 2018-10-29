package hmysjiang.usefulstuffs.enchantment;

import com.google.common.base.Predicate;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.items.ItemLightBattery;
import hmysjiang.usefulstuffs.items.ItemLightBow;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;

public class EnchantmentMoonLight extends Enchantment {
	
	public static final EnumEnchantmentType LIGHT_BOW = EnumHelper.addEnchantmentType(new ResourceLocation(Reference.MOD_ID, "enchantment_xl").toString(), new Predicate<Item>() {
		
		@Override
		public boolean apply(Item input) {
			return input instanceof ItemLightBattery || input instanceof ItemLightBow || input == Items.BOOK;
		}
	});
	public static final EnchantmentMoonLight INSTANCE = new EnchantmentMoonLight();
	public EnchantmentMoonLight() {
		super(Rarity.COMMON, LIGHT_BOW, EntityEquipmentSlot.values());
		setRegistryName(new ResourceLocation(Reference.MOD_ID, "moon_light"));
		setName("usefulstuffs.moonlight.name");
	}
	
	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 10;
	}
	
	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return 100;
	}
	
	@Override
	public int getMaxLevel() {
		return 1;
	}
	
	@Override
	public boolean canApply(ItemStack stack) {
		return stack.getItem() == ModItems.light_battery || stack.getItem() == ModItems.light_bow;
	}

}
