package hmysjiang.usefulstuffs.network.packet;

import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.client.gui.GuiHandler;
import hmysjiang.usefulstuffs.container.ContainerBento;
import hmysjiang.usefulstuffs.items.ItemBento;
import hmysjiang.usefulstuffs.utils.helper.WorldHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class BentoUpdateMode implements IMessage {
	public BentoUpdateMode() {}
	
	public int worldId;
	public int playerEntId;
	
	public BentoUpdateMode(int worldId, int playerEntId) {
		this.worldId = worldId;
		this.playerEntId = playerEntId;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.worldId = buf.readInt();
		this.playerEntId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.worldId);
		buf.writeInt(this.playerEntId);
	}
	
	public static class Handler implements IMessageHandler<BentoUpdateMode, IMessage> {
		public Handler() {}

		@Override
		public IMessage onMessage(BentoUpdateMode message, MessageContext ctx) {
			if (message != null) {
				WorldServer world = (WorldServer) WorldHelper.getServerWorldFromId(message.worldId);
				if (world != null) {
					Entity entity = world.getEntityByID(message.playerEntId);
					if (entity instanceof EntityPlayer) {
						EntityPlayer player = (EntityPlayer) entity;
						ItemStack stack = player.getHeldItemMainhand();
						if (stack.getItem() instanceof ItemBento) {
							int next = stack.getTagCompound().getInteger("Next");
							if (next >= 0) {
								stack.getTagCompound().setInteger("Next", -1);
							}
							else {
								stack.getTagCompound().setInteger("Next", 6);
							}
							if (player.openContainer instanceof ContainerBento)
								((ContainerBento)player.openContainer).saveContainer();
						}
					}
				}
			}
			return null;
		}
		
	}
	
}
