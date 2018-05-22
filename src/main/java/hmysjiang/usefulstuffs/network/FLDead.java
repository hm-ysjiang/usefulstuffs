package hmysjiang.usefulstuffs.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FLDead implements IMessage {
	public FLDead() {}
	
	public int posX;
	public int posY;
	public int posZ;
	
	public FLDead(int posX, int posY, int posZ) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}
	public FLDead(BlockPos pos) {
		this(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		posX = buf.readInt();
		posY = buf.readInt();
		posZ = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(posX);
		buf.writeInt(posY);
		buf.writeInt(posZ);
	}

}
