package hmysjiang.usefulstuffs.items.baubles;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFieryLilyBelt extends ItemLilyBelt {
	
	public ItemFieryLilyBelt() {
		super(Reference.ModItems.BELT_FIERY_LILY.getUnlocalizedName(), Reference.ModItems.BELT_FIERY_LILY.getRegistryName());
	}
	
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		super.onWornTick(itemstack, player);
		player.isImmuneToFire = true;
	}
	
	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		super.onUnequipped(itemstack, player);
		player.isImmuneToFire = false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(I18n.format("usefulstuffs.fiery_lily_belt.tooltip"));
	}
	
}
