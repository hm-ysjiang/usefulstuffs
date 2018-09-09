package hmysjiang.usefulstuffs.client.gui;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.blocks.fermenter.TileEntityMilkFermenter;
import hmysjiang.usefulstuffs.container.ContainerMilkFermenter;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.CapabilityItemHandler;

public class GuiMIlkFermenter extends GuiContainer {
	
	private TileEntityMilkFermenter tile;
	public GuiMIlkFermenter(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		tile = ((ContainerMilkFermenter) inventorySlotsIn).getTile();
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
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/container/fermenter.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		if (tile.isWorking()) {
			int pixels = (int) getProcessedPixels();
			this.drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 48, 176, 0, pixels, 16);
		}
	}
	
	private float getProcessedPixels() {
		ItemStack stack = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(1);
		if (stack.isEmpty()|| stack.getItemDamage() == 8 || !stack.hasTagCompound() || stack.getTagCompound().getInteger("FermentLevel") >= 100) return 0;
		int fermentLevel = stack.getTagCompound().getInteger("FermentLevel");
		return ((float)fermentLevel / 100) * 15 + 1;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRenderer.drawString(I18n.format("usefulstuffs.gui.fermenter.name"), 8, 6, 4210752);
	}

}
