package hmysjiang.usefulstuffs.potion;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.client.SpriteHandler;
import hmysjiang.usefulstuffs.network.PacketHandler;
import hmysjiang.usefulstuffs.network.packet.FieryRemoved;
import hmysjiang.usefulstuffs.utils.helper.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionFieryLily extends Potion {
	public static Potion instance = new PotionFieryLily();
	
	protected PotionFieryLily() {
		super(false, 0xe8812a);
		setPotionName("effect.usefulstuffs.fierylily");
		setRegistryName(new ResourceLocation(Reference.MOD_ID, "fierylily"));
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}
	
	@Override
	public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn,
			AbstractAttributeMap attributeMapIn, int amplifier) {
		entityLivingBaseIn.isImmuneToFire = false;
		PacketHandler.INSTANCE.sendToAll(new FieryRemoved(entityLivingBaseIn));
		super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
	}
	
	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int p_76394_2_) {
		PotionEffect activeEffect = entityLivingBaseIn.getActivePotionEffect(this);
		if (entityLivingBaseIn != null && !entityLivingBaseIn.isSneaking()) {
			if (activeEffect != null) {
				entityLivingBaseIn.isImmuneToFire = true;
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
						}
						else if (entityLivingBaseIn.posY%1 > 0.55) {
							entityLivingBaseIn.motionY = 0.05;
						}
					}
				}
			}	
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
		TextureAtlasSprite sprite = SpriteHandler.fierylily;
		Gui screen = mc.ingameGUI;
		if (screen != null) {
			mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			screen.drawTexturedModalRect(x+4, y+4, sprite, 16, 16);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		TextureAtlasSprite sprite = SpriteHandler.fierylily;
		GuiScreen screen = mc.currentScreen;
		if (screen != null) {
			mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			screen.drawTexturedModalRect(x+7, y+7, sprite, 16, 16);
		}
	}
	
}
