package hmysjiang.usefulstuffs.network.packet;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PlaySound implements IMessage {
	public PlaySound() {}
	
	public int playerEntId;
	public int x;
	public int y;
	public int z;
	public float volume;
	public float pitch;
	public int resourceLength;
	public int categoryLength;
	public String resource;
	public String category;

	public PlaySound(int playerEntityId, BlockPos pos, ResourceLocation sound, SoundCategory category, float volume, float pitch) {
		this.playerEntId = playerEntityId;
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.volume = volume;
		this.pitch = pitch;
		this.resource = sound.toString();
		this.resourceLength = this.resource.length();
		this.category = category.getName();
		this.categoryLength = this.category.length();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.playerEntId = buf.readInt();
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.volume = buf.readFloat();
		this.pitch = buf.readFloat();
		this.resourceLength = buf.readInt();
		this.categoryLength = buf.readInt();
		this.resource = (String) buf.readCharSequence(this.resourceLength, StandardCharsets.UTF_8);
		this.category = (String) buf.readCharSequence(this.categoryLength, StandardCharsets.UTF_8);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.playerEntId);
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeFloat(this.volume);
		buf.writeFloat(this.pitch);
		buf.writeInt(this.resourceLength);
		buf.writeInt(this.categoryLength);
		buf.writeCharSequence(this.resource, StandardCharsets.UTF_8);
		buf.writeCharSequence(this.category, StandardCharsets.UTF_8);
	}
	
	public static class Handler implements IMessageHandler<PlaySound, IMessage>{
		public Handler() {}

		@Override
		public IMessage onMessage(PlaySound message, MessageContext ctx) {
			if (message != null) {
				SoundEvent event = SoundEvent.REGISTRY.getObject(new ResourceLocation(message.resource));
				if (event != null) {
					SoundCategory category = SoundCategory.getByName(message.category);
					if (category != null) {
						Entity player = Minecraft.getMinecraft().world.getEntityByID(message.playerEntId);
						if (player != null && player instanceof EntityPlayer) {
							Minecraft.getMinecraft().world.playSound((EntityPlayer) player, new BlockPos(message.x, message.y, message.z), event, category, message.volume, message.pitch);
						}
					}
				}
			}
			return null;
		}
		
	}
	
}
