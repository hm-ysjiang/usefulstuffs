package hmysjiang.usefulstuffs.potion;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.client.SpriteHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class PotionUmbrella extends Potion {
public static Potion instance = new PotionUmbrella();
	
	protected PotionUmbrella() {
		super(false, 0x10a7ed);
		setPotionName("effect.usefulstuffs.umbrella");
		setRegistryName(new ResourceLocation(Reference.MOD_ID, "umbrella"));
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
		TextureAtlasSprite sprite = SpriteHandler.umbrella;
		Gui screen = mc.ingameGUI;
		if (screen != null) {
			mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			screen.drawTexturedModalRect(x+4, y+4, sprite, 16, 16);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		TextureAtlasSprite sprite = SpriteHandler.umbrella;
		GuiScreen screen = mc.currentScreen;
		if (screen != null) {
			mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			screen.drawTexturedModalRect(x+7, y+7, sprite, 16, 16);
		}
	}
	
	@SubscribeEvent
	public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		PotionEffect activeEffect = event.getEntityLiving().getActivePotionEffect(instance);
		if (activeEffect != null) {
			EntityLivingBase living = event.getEntityLiving();
			if (living.fallDistance > 3)
				if (living.motionY < -0.4)
					living.motionY = -0.4;
		}
	}
	
	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent event) {
		PotionEffect activeEffect = event.getEntityLiving().getActivePotionEffect(instance);
		if (activeEffect != null) {
			float x = activeEffect.getAmplifier() > 0 ? 0.4F : 0.6F;
			event.setDamageMultiplier(event.getDamageMultiplier() * x);
		}
	}

}
