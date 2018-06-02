package hmysjiang.usefulstuffs.items;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemLightShooterCollector extends ItemLightShooter {
	
	public ItemLightShooterCollector() {
		super(Reference.ModItems.LIGHT_SHOOTER_COLLECTOR.getUnlocalizedName(), Reference.ModItems.LIGHT_SHOOTER_COLLECTOR.getRegistryName());
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		tooltip.add(TextFormatting.YELLOW + I18n.format("usefulstuffs.light_shooter.tooltip_c"));
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		
	}
	
}