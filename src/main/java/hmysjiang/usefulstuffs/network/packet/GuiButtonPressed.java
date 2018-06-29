package hmysjiang.usefulstuffs.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class GuiButtonPressed implements IMessage {
	public GuiButtonPressed() {}
	
	public int world;
	public int player;
	public int x;
	public int y;
	public int z;
	public int page;
	
	public GuiButtonPressed(World world, EntityPlayer player, int x, int y, int z, int page) {
		this.world = world.provider.getDimension();
		this.player = player.getEntityId();
		this.x = x;
		this.y = y;
		this.z = z;
		this.page = page;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.world = buf.readInt();
		this.player = buf.readInt();
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.page = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(world);
		buf.writeInt(player);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(page);
	}

}
