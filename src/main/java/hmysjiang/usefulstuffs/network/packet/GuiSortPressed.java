package hmysjiang.usefulstuffs.network.packet;

import hmysjiang.usefulstuffs.blocks.filingcabinets.TileEntityFilingCabinetNBT;
import hmysjiang.usefulstuffs.blocks.filingcabinets.TileEntityFilingCabinetUnstackable;
import hmysjiang.usefulstuffs.utils.helper.WorldHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GuiSortPressed implements IMessage {
	public GuiSortPressed() {}
	
	public int world;
	public int x;
	public int y;
	public int z;
	
	public GuiSortPressed(World world, BlockPos pos) {
		this.world = world.provider.getDimension();
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.world = buf.readInt();
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(world);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}
	
	public static class Handler implements IMessageHandler<GuiSortPressed, IMessage>{
		public Handler() {}	

		@Override
		public IMessage onMessage(GuiSortPressed message, MessageContext ctx) {
			if (message != null) {
				World world = WorldHelper.getServerWorldFromId(message.world);
				if (world == null)	return null;
				BlockPos pos = new BlockPos(message.x, message.y, message.z);
				TileEntity tile = world.getTileEntity(pos);
				if (tile instanceof TileEntityFilingCabinetUnstackable)
					((TileEntityFilingCabinetUnstackable)tile).sort();	
				if (tile instanceof TileEntityFilingCabinetNBT)
					((TileEntityFilingCabinetNBT)tile).sort();	
			}
			return null;
		}
	}

}
