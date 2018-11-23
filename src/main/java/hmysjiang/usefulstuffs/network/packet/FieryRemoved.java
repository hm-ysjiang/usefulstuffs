package hmysjiang.usefulstuffs.network.packet;

import hmysjiang.usefulstuffs.utils.helper.LogHelper;
import hmysjiang.usefulstuffs.utils.helper.WorldHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FieryRemoved implements IMessage {
	public FieryRemoved() {}
	
	public int entityID;
	
	public FieryRemoved(EntityLivingBase entity) {
		entityID = entity.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
	}
	
	public static class Handler implements IMessageHandler<FieryRemoved, IMessage> {
		public Handler() {}

		@Override
		public IMessage onMessage(FieryRemoved message, MessageContext ctx) {
			if (message != null)
				Minecraft.getMinecraft().world.getEntityByID(message.entityID).isImmuneToFire = false;
			return null;
		}
		
	}

}
