package hmysjiang.usefulstuffs.network.handler;

import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.miscs.helper.WorldHelper;
import hmysjiang.usefulstuffs.network.packet.GuiButtonPressed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GuiButtonPressedHandler implements IMessageHandler<GuiButtonPressed, IMessage> {
	public GuiButtonPressedHandler() {}

	@Override
	public IMessage onMessage(GuiButtonPressed message, MessageContext ctx) {
		World world = WorldHelper.getWorldFromId(message.world);
		EntityPlayer player = (EntityPlayer) world.getEntityByID(message.player);
		player.openGui(UsefulStuffs.instance, message.page, world, message.x, message.y, message.z);
		return null;
	}

}
