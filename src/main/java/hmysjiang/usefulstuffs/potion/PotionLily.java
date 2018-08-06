package hmysjiang.usefulstuffs.potion;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.client.SpriteHandler;
import hmysjiang.usefulstuffs.utils.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionLily extends Potion {
	public static Potion instance = new PotionLily();
	public static ResourceLocation resource = new ResourceLocation(Reference.MOD_ID, "textures/misc/lilypad.png");
	
	protected PotionLily() {
		super(false, 0x17b745);
		setPotionName("effect.usefulstuffs.lily");
		setRegistryName(new ResourceLocation(Reference.MOD_ID, "lily"));
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}
	
	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int p_76394_2_) {
		PotionEffect activeEffect = entityLivingBaseIn.getActivePotionEffect(this);
		if (entityLivingBaseIn != null && !entityLivingBaseIn.isSneaking()) {
			if (activeEffect != null) {
				BlockPos entityPos = new BlockPos(entityLivingBaseIn);
				Block blockUnder = entityLivingBaseIn.world.getBlockState(new BlockPos(entityPos.getX(), entityPos.getY()-1, entityPos.getZ())).getBlock();
				Block blockFeet = entityLivingBaseIn.world.getBlockState(entityPos).getBlock();
				Block blockHead = entityLivingBaseIn.world.getBlockState(new BlockPos(entityPos.getX(), entityPos.getY()+1, entityPos.getZ())).getBlock();
				if (!(blockFeet instanceof BlockLiquid && blockHead instanceof BlockLiquid)){
					if (blockFeet instanceof BlockLiquid) {
						if (entityLivingBaseIn.posY%1 > 0.75) {
							entityLivingBaseIn.motionY = 0;
							entityLivingBaseIn.onGround = true;
							entityLivingBaseIn.fallDistance = 0;
							entityLivingBaseIn.extinguish();
						}
						else if (entityLivingBaseIn.posY%1 > 0.55) {
							entityLivingBaseIn.motionY = 0.05;
						}
					}
				}
			}	
		}
	}
	
	@Override
	public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
		TextureAtlasSprite sprite = SpriteHandler.lily;
		Gui screen = mc.ingameGUI;
		if (screen != null) {
			mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			screen.drawTexturedModalRect(x+4, y+4, sprite, 16, 16);
		}
	}
	
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		TextureAtlasSprite sprite = SpriteHandler.lily;
		GuiScreen screen = mc.currentScreen;
		if (screen != null) {
			mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			screen.drawTexturedModalRect(x+7, y+7, sprite, 16, 16);
		}
	}

}
