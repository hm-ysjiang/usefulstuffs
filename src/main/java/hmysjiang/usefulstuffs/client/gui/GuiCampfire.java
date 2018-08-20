package hmysjiang.usefulstuffs.client.gui;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.blocks.campfire.TileEntityCampfire;
import hmysjiang.usefulstuffs.container.ContainerCampfire;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiCampfire extends GuiContainer {
	
	private TileEntityCampfire tile;

	public GuiCampfire(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		
		this.tile = ((ContainerCampfire) inventorySlotsIn).getTile();
		
		this.xSize = 176;
		this.ySize = 132;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/container/campfire.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		if (tile.isBurning()) {
			int s = (int) getBurnLeftScaled(13);
			this.drawTexturedModalRect(this.guiLeft + 99, this.guiTop + 21 + (14 - s), 176, 14 - s, 14, s + 1);
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRenderer.drawString(I18n.format("usefulstuffs.gui.campfire.name"), 8, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("usefulstuffs.gui.campfire.modifier", (3 + MathHelper.ceil(tile.getModifierCounts() / 2))), 8, 18, 4210752);
	}

    private double getBurnLeftScaled(int pixels)
    {
		double i = ((double) tile.getTimeLeft()) / tile.getFuelTime();
        return i * pixels;
    }

}
