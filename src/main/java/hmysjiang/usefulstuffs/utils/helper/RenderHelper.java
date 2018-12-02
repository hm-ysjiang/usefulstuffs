package hmysjiang.usefulstuffs.utils.helper;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class RenderHelper {
	
	public static void renderFluid(Gui gui, FluidStack fluid, int x, int y, int width, int height) {
		int color = fluid.getFluid().getColor();
		GL11.glPushMatrix();
		GlStateManager.color((float) (color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, 1.0F);
		TextureAtlasSprite sprite = getSprite(fluid.getFluid().getStill(fluid));
		for (int i = 0 ; i<width ; i+=16) {
			for (int j = 0 ; j<height ; j+=16) {
				gui.drawTexturedModalRect(x + i, y + j, sprite, (i + 16 > width ? width - i : 16), (j + 16 > height ? height - j : 16));
			}
		}
		GL11.glPopMatrix();
	}
	
	public static TextureAtlasSprite getSprite(ResourceLocation location) {
		return getSprite(location.toString());
	}
	
	public static TextureAtlasSprite getSprite(String location) {
		getRenderEngine().bindTexture(new ResourceLocation("textures/atlas/blocks.png"));
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location);
	}
	
	public static TextureManager getRenderEngine() {
		return Minecraft.getMinecraft().renderEngine;
	}
	
}
