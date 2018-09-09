package hmysjiang.usefulstuffs.items;

import java.util.List;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCheese extends ItemFood {

	public ItemCheese() {
		super(4, 1.625F, false);
		setUnlocalizedName(Reference.ModItems.CHEESE.getUnlocalizedName());
		setRegistryName(Reference.ModItems.CHEESE.getRegistryName());
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if (!worldIn.isRemote && ConfigManager.cheeseDoesBuff) {
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 1));
			player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2400, 1));
		}
		super.onFoodEaten(stack, worldIn, player);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.GOLD + I18n.format("usefulstuffs.cheese.tooltip"));
	}

}
