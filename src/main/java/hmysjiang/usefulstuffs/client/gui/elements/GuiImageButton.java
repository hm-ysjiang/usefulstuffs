package hmysjiang.usefulstuffs.client.gui.elements;

import hmysjiang.usefulstuffs.utils.helper.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiImageButton extends GuiButton {
	
	private ResourceLocation image;
	private int imgoffsetX;
	private int imgoffsetY;
	private int imgWidth;
	private int imgHeight;
	
	public GuiImageButton(int buttonId, int x, int y, ResourceLocation image, int imgoffsetX, int imgoffsetY, int imgWidth, int imgHeight) {
		super(buttonId, x, y, "");
		this.image = image;
		this.imgoffsetX = imgoffsetX;
		this.imgoffsetY = imgoffsetY;
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
	}

	public GuiImageButton(int buttonId, int x, int y, int widthIn, int heightIn, ResourceLocation image, int imgoffsetX, int imgoffsetY, int imgWidth, int imgHeight) {
		super(buttonId, x, y, widthIn, heightIn, "");
		this.image = image;
		this.imgoffsetX = imgoffsetX;
		this.imgoffsetY = imgoffsetY;
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		super.drawButton(mc, mouseX, mouseY, partialTicks);
		this.drawTexturedModalRect(x + imgoffsetX, y + imgoffsetY, RenderHelper.getSprite(image), imgWidth, imgHeight);
	}
	
	public void setImage(ResourceLocation newImg) {
		this.image = newImg;
	}

}
