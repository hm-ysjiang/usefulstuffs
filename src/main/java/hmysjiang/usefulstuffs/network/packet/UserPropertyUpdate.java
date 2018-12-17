package hmysjiang.usefulstuffs.network.packet;

import hmysjiang.usefulstuffs.blocks.universaluser.TileEntityUniversalUser;
import hmysjiang.usefulstuffs.utils.helper.WorldHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class UserPropertyUpdate implements IMessage {
	public UserPropertyUpdate() {}
	
	public int buttonid;
	public int worldid;
	public int x;
	public int y;
	public int z;
	public boolean counter;
	
	public UserPropertyUpdate(int buttonid, int worldid, int x, int y, int z, boolean counter) {
		this.buttonid = buttonid;
		this.worldid = worldid;
		this.x = x;
		this.y = y;
		this.z = z;
		this.counter = counter;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		buttonid = buf.readInt();
		worldid = buf.readInt();
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		counter = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(buttonid);
		buf.writeInt(worldid);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeBoolean(counter);
	}
	
	public static class Handler implements IMessageHandler<UserPropertyUpdate, IMessage> {
		public Handler() {}

		@Override
		public IMessage onMessage(UserPropertyUpdate message, MessageContext ctx) {
			if (message != null) {
				TileEntityUniversalUser tile = (TileEntityUniversalUser) WorldHelper.getServerWorldFromId(message.worldid).getTileEntity(new BlockPos(message.x, message.y, message.z));
				switch (message.buttonid) {
				case 0:
					tile.activation = tile.activation.next(message.counter);
					break;
				case 1:
					tile.operateSpeed = tile.operateSpeed.next(message.counter);
					tile.resetWorkTime();
					break;
				case 2:
					tile.select = tile.select.next(message.counter);
					break;
				case 3:
					tile.button = tile.button.next(message.counter);
					break;
				case 4:
					tile.redstone = tile.redstone.next(message.counter);
					break;
				}
			}
			return null;
		}
		
	}

}
