package hmysjiang.usefulstuffs.client.gui;

import java.io.IOException;
import java.util.Arrays;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.blocks.universaluser.TileEntityUniversalUser.Activation;
import hmysjiang.usefulstuffs.blocks.universaluser.TileEntityUniversalUser.Button;
import hmysjiang.usefulstuffs.blocks.universaluser.TileEntityUniversalUser.OperateSpeed;
import hmysjiang.usefulstuffs.blocks.universaluser.TileEntityUniversalUser.Redstone;
import hmysjiang.usefulstuffs.blocks.universaluser.TileEntityUniversalUser.Select;
import hmysjiang.usefulstuffs.client.gui.elements.GuiImageButton;
import hmysjiang.usefulstuffs.container.ContainerUniversalUser;
import hmysjiang.usefulstuffs.network.PacketHandler;
import hmysjiang.usefulstuffs.network.packet.UserPropertyUpdate;
import hmysjiang.usefulstuffs.utils.helper.RenderHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public class GuiUniversalUser extends GuiContainer {
	
	private OperateSpeed speed;
	private Activation activation;
	private Button button;
	private Select select;
	private Redstone redstone;

	public GuiUniversalUser(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		
		this.xSize = 176;
		this.ySize = 198;
		
		this.speed = ((ContainerUniversalUser)inventorySlots).getTile().operateSpeed;
		this.activation = ((ContainerUniversalUser)inventorySlots).getTile().activation;
		this.button = ((ContainerUniversalUser)inventorySlots).getTile().button;
		this.select = ((ContainerUniversalUser)inventorySlots).getTile().select;
		this.redstone = ((ContainerUniversalUser)inventorySlots).getTile().redstone;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		GuiImageButton redstone_mode = new GuiImageButton(4, this.guiLeft + 152, this.guiTop + 16, 16, 16, redstone.getImgLocation(), 0, 0, 16, 16);
		
		this.buttonList.add(new GuiButton(0, this.guiLeft + 8, this.guiTop + 77, 80, 16, activation.toString()));
		this.buttonList.add(new GuiButton(1, this.guiLeft + 88, this.guiTop + 77, 80, 16, speed.toString()));
		this.buttonList.add(new GuiButton(2, this.guiLeft + 8, this.guiTop + 93, 80, 16, select.toString()));
		this.buttonList.add(new GuiButton(3, this.guiLeft + 88, this.guiTop + 93, 80, 16, button.toString()));
		this.buttonList.add(redstone_mode);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
		if (mouseX >= this.guiLeft + 152 && mouseX < this.guiLeft + 168 && mouseY >= this.guiTop + 16 && mouseY < this.guiTop + 32)
			this.drawHoveringText(redstone.toString(), mouseX, mouseY);
		else if (mouseX >= this.guiLeft + 8 && mouseX < this.guiLeft + 11 && mouseY >= this.guiTop + 17 && mouseY < this.guiTop + 69) {
			IEnergyStorage storage = ((ContainerUniversalUser)inventorySlots).getCapacitor();
			this.drawHoveringText(Arrays.asList("Capacitor", ("" + storage.getEnergyStored() + "/" + storage.getMaxEnergyStored() + "FE")), mouseX, mouseY);
		}
		else if (mouseX >= this.guiLeft + 17 && mouseX < this.guiLeft + 20 && mouseY >= this.guiTop + 17 && mouseY < this.guiTop + 69) {
			IEnergyStorage storage = ((ContainerUniversalUser)inventorySlots).getTile().getInnerCapacitor();
			this.drawHoveringText(Arrays.asList("InnerCapacitor", ("" + storage.getEnergyStored() + "/" + storage.getMaxEnergyStored() + "FE")), mouseX, mouseY);
		}
		else if (mouseX >= this.guiLeft + 26 && mouseX < this.guiLeft + 42 && mouseY >= this.guiTop + 17 && mouseY < this.guiTop + 69) {
			IFluidTank tank = ((ContainerUniversalUser)inventorySlots).getTank();
			if (tank.getFluid() != null)
				this.drawHoveringText(Arrays.asList(I18n.format(tank.getFluid().getUnlocalizedName()), ("" + tank.getFluidAmount() + "/" + tank.getCapacity() + "mB")), mouseX, mouseY);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/container/user.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		drawEnergy();
		drawFluid();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRenderer.drawString(I18n.format("usefulstuffs.gui.user.name"), 8, 6, 4210752);
	}

	private void drawFluid() {
		IFluidTank tank = ((ContainerUniversalUser)inventorySlots).getTank();
		FluidStack fluid = tank.getFluid();
		if (fluid == null || fluid.getFluid() == null || fluid.getFluid().getStill(fluid) == null) {
			this.drawTexturedModalRect(this.guiLeft + 26, this.guiTop + 17, 179, 0, 16, 52);
			return;
		}
		int pixels = getScaled(52, tank.getFluidAmount(), 4000);
		RenderHelper.renderFluid(this, fluid, this.guiLeft + 26, this.guiTop + 69 - pixels, 16, pixels);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/container/user.png"));
		this.drawTexturedModalRect(this.guiLeft + 26, this.guiTop + 17, 179, 0, 16, 52);
	}
	
	private void drawEnergy() {
		IEnergyStorage capacitor = ((ContainerUniversalUser)inventorySlots).getCapacitor();
		IEnergyStorage innerCapacitor = ((ContainerUniversalUser)inventorySlots).getTile().getInnerCapacitor();
		int pixels = getScaled(52, capacitor.getEnergyStored(), capacitor.getMaxEnergyStored());
		this.drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 69 - pixels, 176, 0, 3, pixels);
		pixels = getScaled(52, innerCapacitor.getEnergyStored(), innerCapacitor.getMaxEnergyStored());
		this.drawTexturedModalRect(this.guiLeft + 17, this.guiTop + 69 - pixels, 176, 0, 3, pixels);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		PacketHandler.INSTANCE.sendToServer(new UserPropertyUpdate(button.id, ((ContainerUniversalUser)inventorySlots).getTile().getWorld().provider.getDimension(), ((ContainerUniversalUser)inventorySlots).getTile().getPos().getX(), ((ContainerUniversalUser)inventorySlots).getTile().getPos().getY(), ((ContainerUniversalUser)inventorySlots).getTile().getPos().getZ()));
		switch (button.id) {
		case 0:
			this.activation = this.activation.next();
			this.buttonList.get(button.id).displayString = this.activation.toString();
			break;
		case 1:
			this.speed = this.speed.next();
			this.buttonList.get(button.id).displayString = this.speed.toString();
			break;
		case 2:
			this.select = this.select.next();
			this.buttonList.get(button.id).displayString = this.select.toString();
			break;
		case 3:
			this.button = this.button.next();
			this.buttonList.get(button.id).displayString = this.button.toString();
			break;
		case 4:
			this.redstone = this.redstone.next();
			((GuiImageButton) this.buttonList.get(button.id)).setImage(redstone.getImgLocation());
			break;
		}
	}
	
	protected int getScaled(int pixels, int amount, int cap) {
		if (amount == 0)
			return 0;
		if (amount == cap)
			return pixels;
		return (int) ((double) amount / cap * pixels);
	}

}
