package hmysjiang.usefulstuffs.client.gui;

import java.io.IOException;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.container.ContainerFilingCabinet;
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

public class GuiFilingCabinet extends GuiContainer {
	
	protected int page;
	private static final int buttonSize = 20;
	
	public GuiFilingCabinet(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		
		this.page = ((ContainerFilingCabinet)inventorySlotsIn).getPage();

		this.xSize = 336;
		this.ySize = 222;
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
		this.fontRendererObj.drawString(I18n.format("usefulstuffs.gui.filing_cabinet.name"), 8, 6, 4210752);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		if (page == 0) {
			this.buttonList.add(new GuiButton(1, this.guiLeft + 247, this.guiTop + 132, buttonSize, buttonSize, ">"));
		}
		else if (page == 9) {
			this.buttonList.add(new GuiButton(8, this.guiLeft + 52, this.guiTop + 132, buttonSize, buttonSize, "<"));
		}
		else if (page<9 && page>0) {
			this.buttonList.add(new GuiButton(page - 1, this.guiLeft + 52, this.guiTop + 132, buttonSize, buttonSize, "<"));
			this.buttonList.add(new GuiButton(page + 1, this.guiLeft + 247, this.guiTop + 132, buttonSize, buttonSize, ">"));
		}
		
		this.buttonList.add(new GuiButton(10, this.guiLeft + 270, this.guiTop + 132, buttonSize, buttonSize, "S"));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id != 10) {
			EntityPlayer player = ((ContainerFilingCabinet)this.inventorySlots).getPlayer();
			BlockPos pos = ((ContainerFilingCabinet)this.inventorySlots).getPos();
			PacketHandler.INSTANCE.sendToServer(new GuiButtonPressed(player.world, player, pos.getX(), pos.getY(), pos.getZ(), button.id));	
		}
		else {
			EntityPlayer player = ((ContainerFilingCabinet)this.inventorySlots).getPlayer();
			BlockPos pos = ((ContainerFilingCabinet)this.inventorySlots).getPos();
			PacketHandler.INSTANCE.sendToServer(new GuiSortPressed(player.world, pos));
//			PacketHandler.INSTANCE.sendToServer(new GuiButtonPressed(player.worldObj, player, pos.getX(), pos.getY(), pos.getZ(), page));	
		}
		super.actionPerformed(button);
	}
	
}
