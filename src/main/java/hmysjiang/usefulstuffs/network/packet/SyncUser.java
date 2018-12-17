package hmysjiang.usefulstuffs.network.packet;

import hmysjiang.usefulstuffs.blocks.universaluser.TileEntityUniversalUser;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncUser implements IMessage {
	public SyncUser() {}
	
	public int x;
	public int y;
	public int z;
	public NBTTagCompound tag;
	
	public SyncUser(int x, int y, int z, NBTTagCompound tag) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.tag = tag;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		tag = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		ByteBufUtils.writeTag(buf, tag);
	}
	
	public static class Handler implements IMessageHandler<SyncUser, IMessage> {
		public Handler() {}

		@Override
		public IMessage onMessage(SyncUser message, MessageContext ctx) {
			if (message != null) {
				BlockPos pos = new BlockPos(message.x, message.y, message.z);
				if (Minecraft.getMinecraft().world.isAreaLoaded(pos, pos)){
					TileEntity tile = Minecraft.getMinecraft().world.getTileEntity(pos);
					if (tile != null && tile instanceof TileEntityUniversalUser) {
						tile.readFromNBT(message.tag);
					}
				}
			}
			return null;
		}
		
	}

}
