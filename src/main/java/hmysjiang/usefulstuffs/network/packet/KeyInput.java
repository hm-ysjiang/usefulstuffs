package hmysjiang.usefulstuffs.network.packet;

import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.items.baubles.ItemBackpack;
import hmysjiang.usefulstuffs.items.baubles.ItemStorageBag;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
				EntityPlayer player = ctx.getServerHandler().player;
				IBaublesItemHandler baubleInv = player.getCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null);
				if (baubleInv.getStackInSlot(5) != null) {
					if (baubleInv.getStackInSlot(5).isItemEqual(new ItemStack(ModItems.bag_storage)))
						ItemStorageBag.onKeyBindingPressed(player, baubleInv.getStackInSlot(5));
					else if (baubleInv.getStackInSlot(5).getItem() instanceof ItemBackpack)
						ItemBackpack.onKeyBindingPressed(player);
				}
				break;
			}
			return null;
		}
	}

}
