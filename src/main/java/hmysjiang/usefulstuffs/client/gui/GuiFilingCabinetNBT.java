package hmysjiang.usefulstuffs.client.gui;

import java.io.IOException;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.container.ContainerFilingCabinetNBT;
import hmysjiang.usefulstuffs.network.PacketHandler;
import hmysjiang.usefulstuffs.network.packet.GuiButtonPressed;
import hmysjiang.usefulstuffs.network.packet.GuiSortPressed;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class GuiFilingCabinetNBT extends GuiContainer {
	private static final int BUTTON_SIZE = 20;

	protected int page;
	
	public GuiFilingCabinetNBT(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		
		this.page = ((ContainerFilingCabinetNBT)inventorySlotsIn).getPage();

		this.xSize = 336;
		this.ySize = 222;
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
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/container/filing_cabinet_left.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 256, this.ySize);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/container/filing_cabinet_right.png"));
		this.drawTexturedModalRect(this.guiLeft + 256, this.guiTop, 0, 0, 80, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRenderer.drawString(I18n.format("usefulstuffs.gui.filing_cabinet.name"), 8, 6, 4210752);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		if (page == 0) {
			this.buttonList.add(new GuiButton(1, this.guiLeft + 247, this.guiTop + 132, BUTTON_SIZE, BUTTON_SIZE, ">"));
		}
		else if (page == 9) {
			this.buttonList.add(new GuiButton(8, this.guiLeft + 52, this.guiTop + 132, BUTTON_SIZE, BUTTON_SIZE, "<"));
		}
		else if (page<9 && page>0) {
			this.buttonList.add(new GuiButton(page - 1, this.guiLeft + 52, this.guiTop + 132, BUTTON_SIZE, BUTTON_SIZE, "<"));
			this.buttonList.add(new GuiButton(page + 1, this.guiLeft + 247, this.guiTop + 132, BUTTON_SIZE, BUTTON_SIZE, ">"));
		}
		
		this.buttonList.add(new GuiButton(10, this.guiLeft + 270, this.guiTop + 132, BUTTON_SIZE, BUTTON_SIZE, "S"));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id != 10) {
			EntityPlayer player = ((ContainerFilingCabinetNBT)this.inventorySlots).getPlayer();
			BlockPos pos = ((ContainerFilingCabinetNBT)this.inventorySlots).getPos();
			PacketHandler.INSTANCE.sendToServer(new GuiButtonPressed(player.world, player, pos.getX(), pos.getY(), pos.getZ(), button.id + 18));	
		}
		else {
			EntityPlayer player = ((ContainerFilingCabinetNBT)this.inventorySlots).getPlayer();
			BlockPos pos = ((ContainerFilingCabinetNBT)this.inventorySlots).getPos();
			PacketHandler.INSTANCE.sendToServer(new GuiSortPressed(player.world, pos));	
		}
		super.actionPerformed(button);
	}
}
