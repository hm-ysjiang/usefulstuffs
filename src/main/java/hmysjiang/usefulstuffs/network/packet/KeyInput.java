package hmysjiang.usefulstuffs.network.packet;

import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import hmysjiang.usefulstuffs.init.ModItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.ItemStackHandler;

public class KeyInput implements IMessage {
	public KeyInput() {}
	
	public int keyIndex;
	
	public KeyInput(int keyIndex) {
		this.keyIndex = keyIndex;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.keyIndex = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(keyIndex);
	}
	
	public static class Handler implements IMessageHandler<KeyInput, IMessage>{
		public Handler() {}

		@Override
		public IMessage onMessage(KeyInput message, MessageContext ctx) {
			switch(message.keyIndex) {
			case 0:
				EntityPlayer player = ctx.getServerHandler().playerEntity;
				IBaublesItemHandler baubleInv = player.getCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null);
				for (int i = 0 ; i<baubleInv.getSlots() ; i++) {
					if (baubleInv.getStackInSlot(i) != null && baubleInv.getStackInSlot(i).isItemEqual(new ItemStack(ModItems.body_storage))) {
						ItemStack bauble = baubleInv.getStackInSlot(i);
						if (player.isSneaking()) {}
						else {
							if (!bauble.hasTagCompound() || !bauble.getTagCompound().hasKey("Current") || !bauble.getTagCompound().hasKey("Cont")) {
								//create a new handler, deal with the contents
								ItemStackHandler handler = new ItemStackHandler(36);
								IInventory inv = player.inventory;
								for (int j = 0 ; j<9 ; j++) {
									handler.setStackInSlot(j, inv.getStackInSlot(j));
									inv.removeStackFromSlot(j);
								}
								//seal the information into nbt
								NBTTagCompound compound = new NBTTagCompound();
								compound.setInteger("Current", 1);
								compound.setTag("Cont", handler.serializeNBT());
								bauble.setTagCompound(compound);
							}
							else {
								//retrieve the information from nbt
								NBTTagCompound compound = bauble.getTagCompound();
								ItemStackHandler handler = new ItemStackHandler(36);
								handler.deserializeNBT(compound.getCompoundTag("Cont"));
								int cur = compound.getInteger("Current");
								IInventory inv = player.inventory;
								//copy the items in player'shotbar into handler
								for (int j = 0 ; j<9 ; j++) {
									handler.setStackInSlot(cur * 9 + j, inv.getStackInSlot(j));
								}
								cur ++;	cur %= 4;
								for (int j = 0 ; j<9 ; j++) {
									ItemStack stack = handler.getStackInSlot(cur * 9 + j);
									if (stack == null) {
										inv.removeStackFromSlot(j);
									}
									else {
										inv.setInventorySlotContents(j, stack);
									}
								}
								//update the nbt data
								compound.setTag("Cont", handler.serializeNBT());
								compound.setInteger("Current", cur);
								bauble.setTagCompound(compound);
							}
						}
					}
				}
				break;
			}
			return null;
		}
	}

}
