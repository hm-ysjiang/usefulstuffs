package hmysjiang.usefulstuffs.container;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public abstract class ContainerItem extends ContainerBase {
	
	protected static ItemStackHandler getDeserializedHandler(ItemStack stack, int size) {
		ItemStackHandler handler = new ItemStackHandler(size);
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Cont"))
			handler.deserializeNBT(stack.getTagCompound().getCompoundTag("Cont"));
		return handler;
	}
	
	protected EntityPlayer player;
	protected ItemStack stack;
	protected int size;
	protected int blocked = -1;
	
	public ContainerItem(EntityPlayer player, IInventory inv, ItemStack stack, int size) {
		super(inv, getDeserializedHandler(stack, size));
		this.player = player;
		this.stack = stack;
		this.size = size;
		if (inv instanceof InventoryPlayer) 
			blocked = size + 27 + ((InventoryPlayer)inv).currentItem;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		saveContainer();
	}
	
	public void saveContainer() {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setTag("Cont", handler.serializeNBT());
	}
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		if (slotId  <0 || slotId > inventorySlots.size())
			return super.slotClick(slotId, dragType, clickTypeIn, player);
		if (slotId == blocked)
			return inventorySlots.get(slotId).getStack();
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}
	
	@SideOnly(Side.CLIENT)
	public String getStackDisplayName() {
		if (stack.hasDisplayName())
			return stack.getDisplayName();
		return I18n.format(stack.getUnlocalizedName() + ".name");
	}
	
	public ItemStack getStack() {
		return stack;
	}
	
	public EntityPlayer getPlayer() {
		return this.player;
	}

}