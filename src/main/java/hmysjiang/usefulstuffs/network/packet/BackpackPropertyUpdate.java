package hmysjiang.usefulstuffs.network.packet;

import hmysjiang.usefulstuffs.items.baubles.ItemMiningBackpack;
import hmysjiang.usefulstuffs.utils.helper.WorldHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class BackpackPropertyUpdate implements IMessage {
	public BackpackPropertyUpdate() {}
	
	public int worldId;
	public int playerId;
	public boolean auto;
	public boolean quickDepo;

	public BackpackPropertyUpdate(World world, EntityPlayer player, boolean auto, boolean quickDepo) {
		this.worldId = world.provider.getDimension();
		this.playerId = player.getEntityId();
		this.auto = auto;
		this.quickDepo = quickDepo;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.worldId = buf.readInt();
		this.playerId = buf.readInt();
		this.auto = buf.readBoolean();
		this.quickDepo = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(worldId);
		buf.writeInt(playerId);
		buf.writeBoolean(auto);
		buf.writeBoolean(quickDepo);
	}
	
	public static class Handler implements IMessageHandler<BackpackPropertyUpdate, IMessage> {
		public Handler() {}

		@Override
		public IMessage onMessage(BackpackPropertyUpdate message, MessageContext ctx) {
			EntityPlayer player = (EntityPlayer) WorldHelper.getServerWorldFromId(message.worldId).getEntityByID(message.playerId);
			ItemStack stack = player.getHeldItemMainhand();
			ItemMiningBackpack.updateProperties(stack, message.auto, message.quickDepo);
			return null;
		}
		
	}
	
}
