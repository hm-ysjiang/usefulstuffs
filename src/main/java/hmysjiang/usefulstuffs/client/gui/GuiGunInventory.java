package hmysjiang.usefulstuffs.client.gui;

import hmysjiang.usefulstuffs.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiGunInventory extends GuiContainer {

	public GuiGunInventory(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/container/gun_inventory.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

}
