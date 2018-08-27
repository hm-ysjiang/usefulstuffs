package hmysjiang.usefulstuffs.client.gui;

import java.io.IOException;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.container.ContainerItem;
import hmysjiang.usefulstuffs.container.ContainerMiningBackpackConfig;
import hmysjiang.usefulstuffs.items.baubles.ItemMiningBackpack;
import hmysjiang.usefulstuffs.network.PacketHandler;
import hmysjiang.usefulstuffs.network.packet.BackpackPropertyUpdate;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiMiningBackpackConfig extends GuiContainer {
	
	protected boolean auto;
	protected boolean quickDepo;

	public GuiMiningBackpackConfig(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		auto = ((ContainerMiningBackpackConfig)this.inventorySlots).getStack().getTagCompound().getBoolean("Auto");
		quickDepo = ((ContainerMiningBackpackConfig)this.inventorySlots).getStack().getTagCompound().getBoolean("QuickDepo");
		
		this.xSize = 176;
		this.ySize = 198;
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
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/container/backpack_config.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRenderer.drawString(((ContainerMiningBackpackConfig) this.inventorySlots).getStack().getDisplayName(), 8, 6, 4210752);
		this.fontRenderer.drawString("Extra Whitelist", 8, 17, 4210752);
		this.fontRenderer.drawString("Blacklist", 8, 48, 4210752);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(new GuiButton(0, this.guiLeft + 7, this.guiTop + 77, 162, 16, "AutoPickup = " + String.valueOf(auto)));
		this.buttonList.add(new GuiButton(1, this.guiLeft + 7, this.guiTop + 95, 162, 16, "QuickDeposit = " + String.valueOf(quickDepo)));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		EntityPlayer player = ((ContainerMiningBackpackConfig)inventorySlots).getPlayer();
		switch (button.id) {
		case 0:
			auto = !auto;
			this.buttonList.get(0).displayString = ("AutoPickup = " + String.valueOf(auto));
			ItemMiningBackpack.updateProperties(((ContainerMiningBackpackConfig)this.inventorySlots).getStack(), auto, quickDepo);
			PacketHandler.INSTANCE.sendToServer(new BackpackPropertyUpdate(player.world, player, auto, quickDepo));
			break;
		case 1:
			quickDepo = !quickDepo;
			this.buttonList.get(1).displayString = ("QuickDeposit = " + String.valueOf(quickDepo));
			ItemMiningBackpack.updateProperties(((ContainerMiningBackpackConfig)this.inventorySlots).getStack(), auto, quickDepo);
			PacketHandler.INSTANCE.sendToServer(new BackpackPropertyUpdate(player.world, player, auto, quickDepo));
			break;
		}
		super.actionPerformed(button);
	}

}
