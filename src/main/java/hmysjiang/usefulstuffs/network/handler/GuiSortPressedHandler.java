package hmysjiang.usefulstuffs.network.handler;

import hmysjiang.usefulstuffs.miscs.helper.WorldHelper;
import hmysjiang.usefulstuffs.network.packet.GuiSortPressed;
import hmysjiang.usefulstuffs.tileentity.TileEntityFilingCabinet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GuiSortPressedHandler implements IMessageHandler<GuiSortPressed, IMessage> {
	public GuiSortPressedHandler() {}	

	@Override
	public IMessage onMessage(GuiSortPressed message, MessageContext ctx) {
		World world = WorldHelper.getWorldFromId(message.world);
		BlockPos pos = new BlockPos(message.x, message.y, message.z);
		((TileEntityFilingCabinet)world.getTileEntity(pos)).sort();
		return null;
	}

}
