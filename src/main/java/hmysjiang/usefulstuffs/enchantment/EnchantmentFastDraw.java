package hmysjiang.usefulstuffs.enchantment;

import java.util.ArrayList;
import java.util.List;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.Reference;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class EnchantmentFastDraw extends Enchantment {

	public static final EnchantmentFastDraw INSTANCE = new EnchantmentFastDraw();
	
	private List<EntityPlayer> players = new ArrayList<EntityPlayer>();
	protected EnchantmentFastDraw() {
		super(Rarity.COMMON, EnumEnchantmentType.BOW, EntityEquipmentSlot.values());
		setRegistryName(new ResourceLocation(Reference.MOD_ID, "fast_draw"));
		setName("usefulstuffs.fast_draw.name");
	}
	
	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 20;
	}
	
	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return 50;
	}
	
	@Override
	public int getMaxLevel() {
		return 1;
	}
	
	@SubscribeEvent
	public void onArrowNock(ArrowNockEvent event) {
		if (ConfigManager.functionFastdraw && !players.contains(event.getEntityPlayer()) && EnchantmentHelper.getEnchantmentLevel(INSTANCE, event.getBow()) > 0)
			players.add(event.getEntityPlayer());
	}
	
	@SubscribeEvent
	public void onArrowLoose(ArrowLooseEvent event) {
		if (ConfigManager.functionFastdraw && players.contains(event.getEntityPlayer()))
			players.remove(event.getEntityPlayer());
	}
	
	@SubscribeEvent
	public void onPlayerLogOut(PlayerLoggedOutEvent event) {
		if (ConfigManager.functionFastdraw && players.contains(event.player))
			players.remove(event.player);
	}
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		if (ConfigManager.functionFastdraw && players.contains(event.player)) {
			if (!event.player.isHandActive() || EnchantmentHelper.getEnchantmentLevel(INSTANCE, event.player.getActiveItemStack()) <= 0)
				players.remove(event.player);
			else {
				event.player.activeItemStackUseCount--;
			}
		}
	}

}
