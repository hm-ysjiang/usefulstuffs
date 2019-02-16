package hmysjiang.usefulstuffs.items;

import java.util.List;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.enchantment.EnchantmentMoonLight;
import hmysjiang.usefulstuffs.utils.ILightChargable;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLightBattery extends Item implements ILightChargable, IBauble {
	
	public static int getChargedEnergy(ItemStack stack) {
		if (stack.isEmpty() || !(stack.getItem() instanceof ItemLightBattery))
			return 0;
		return stack.getMaxDamage() - stack.getItemDamage();
	}
	
	/***
	 * 
	 * @param stack
	 * @param amount
	 * @param simulate
	 * @return The amount that was actually drained (or would be drained if simulating)
	 */
	public static int drainEnergy(ItemStack stack, int amount, boolean simulate) {
		if (stack.isEmpty() || !(stack.getItem() instanceof ItemLightBattery))
			return 0;
		int charge = getChargedEnergy(stack);
		if (charge < amount) {
			if (!simulate)
				stack.setItemDamage(stack.getMaxDamage());
			return charge;
		}
		if (!simulate)
			stack.setItemDamage(stack.getItemDamage() + amount);
		return amount;
	}
	
	public ItemLightBattery() {
		setUnlocalizedName(Reference.ModItems.LIGHT_BATTERY.getUnlocalizedName());
		setRegistryName(Reference.ModItems.LIGHT_BATTERY.getRegistryName());
		setMaxStackSize(1);
		setMaxDamage(32000);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		charge(worldIn, stack, entityIn.getPosition());
		if (entityIn instanceof EntityPlayer)
			chargeInventory(stack, (EntityPlayer) entityIn, itemSlot);
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		charge(player.world, itemstack, player.getPosition());
		if (player instanceof EntityPlayer)
			chargeInventory(itemstack, (EntityPlayer) player, -1);
	}
	
	private void chargeInventory(ItemStack battery, EntityPlayer player, int itemSlot) {
		for (ItemStack stack: player.inventory.mainInventory)
			if (stack.getItem() instanceof ILightChargable && !(stack.getItem() instanceof ItemLightBattery))
				chargeItem(battery, stack);
		for (ItemStack stack: player.inventory.offHandInventory)
			if (stack.getItem() instanceof ILightChargable && !(stack.getItem() instanceof ItemLightBattery))
				chargeItem(battery, stack);
	}
	
	private void chargeItem(ItemStack battery, ItemStack stack) {
		int charge = getChargedEnergy(battery);
		if (charge > 0) {
			if (charge > 10)
				charge = 10;
			if (charge > stack.getItemDamage())
				charge = stack.getItemDamage();
			battery.setItemDamage(battery.getItemDamage() + charge);
			stack.setItemDamage(stack.getItemDamage() - charge);
		}
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab == UsefulStuffs.TAB) {
			items.add(new ItemStack(this, 1, 0));
			items.add(new ItemStack(this, 1, getMaxDamage()));
		}
	}
	
	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		return 0xfffa00;
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.TRINKET;
	}
	
	@Override
	public int getItemEnchantability() {
		return 3;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment == EnchantmentMoonLight.INSTANCE;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("usefulstuffs.light_battery.tooltip_1"));
		tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.light_battery.tooltip_charge", String.valueOf(getChargedEnergy(stack))));
		tooltip.add(I18n.format("usefulstuffs.light_battery.tooltip_2"));
	}
	
}
