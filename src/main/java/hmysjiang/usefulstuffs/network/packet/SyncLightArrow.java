package hmysjiang.usefulstuffs.network.packet;

import hmysjiang.usefulstuffs.entity.EntityLightArrow;
import hmysjiang.usefulstuffs.utils.helper.LogHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncLightArrow implements IMessage {
	public SyncLightArrow() {}
	
	public int entityId;
	public double posX;
	public double posY;
	public double posZ;
	public double motionX;
	public double motionY;
	public double motionZ;
	
	public SyncLightArrow(EntityLightArrow entity) {
		this.entityId = entity.getEntityId();
		this.posX = entity.posX;
		this.posY = entity.posY;
		this.posZ = entity.posZ;
		this.motionX = entity.motionX;
		this.motionY = entity.motionY;
		this.motionZ = entity.motionZ;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityId = buf.readInt();
		this.posX = buf.readDouble();
		this.posY = buf.readDouble();
		this.posZ = buf.readDouble();
		this.motionX = buf.readDouble();
		this.motionY = buf.readDouble();
		this.motionZ = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.entityId);
		buf.writeDouble(this.posX);
		buf.writeDouble(this.posY);
		buf.writeDouble(this.posZ);
		buf.writeDouble(this.motionX);
		buf.writeDouble(this.motionY);
		buf.writeDouble(this.motionZ);
	}
	
	public static class Handler implements IMessageHandler<SyncLightArrow, IMessage> {
		public Handler() {}

		@Override
		public IMessage onMessage(SyncLightArrow message, MessageContext ctx) {
			Entity entity = Minecraft.getMinecraft().world.getEntityByID(message.entityId);
			if (entity != null) {
				entity.posX = message.posX;
				entity.posY = message.posY;
				entity.posZ = message.posZ;
				entity.motionX = message.motionX;
				entity.motionY = message.motionY;
				entity.motionZ = message.motionZ;
			}
			return null;
		}
		
	}

}
