package hmysjiang.usefulstuffs.client.gui;

import java.io.IOException;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.client.gui.elements.GuiSpriteButton;
import hmysjiang.usefulstuffs.container.ContainerBento;
import hmysjiang.usefulstuffs.container.ContainerItem;
import hmysjiang.usefulstuffs.network.PacketHandler;
import hmysjiang.usefulstuffs.network.packet.BentoUpdateMode;
import hmysjiang.usefulstuffs.utils.helper.LogHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiBento extends GuiContainer {

	public GuiBento(ContainerBento inventorySlotsIn) {
		super(inventorySlotsIn);
		
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
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/container/bento.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRenderer.drawString(((ContainerItem)this.inventorySlots).getStackDisplayName(), 8, 6, 4210752);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		int next = ((ContainerBento)inventorySlots).getStack().getTagCompound().getInteger("Next");
		this.buttonList.add(new GuiSpriteButton(0, this.guiLeft + 148, this.guiTop + 19, 18, 18, next >= 0 ? new ResourceLocation(Reference.MOD_ID, "misc/round") : new ResourceLocation(Reference.MOD_ID, "misc/forward"), 1, 1, 16, 16));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 0) {
			int next = ((ContainerBento)inventorySlots).getStack().getTagCompound().getInteger("Next");
			if (next >= 0) {
				((ContainerBento)inventorySlots).getStack().getTagCompound().setInteger("Next", -1);
			}
			else {
				((ContainerBento)inventorySlots).getStack().getTagCompound().setInteger("Next", 6);
			}
			((GuiSpriteButton)this.buttonList.get(0)).setImage(next < 0 ? new ResourceLocation(Reference.MOD_ID, "misc/round") : new ResourceLocation(Reference.MOD_ID, "misc/forward"));
			PacketHandler.INSTANCE.sendToServer(new BentoUpdateMode(((ContainerBento)inventorySlots).getPlayer().world.provider.getDimension(), ((ContainerBento)inventorySlots).getPlayer().getEntityId()));
		}
	}

}