package hmysjiang.usefulstuffs.network.handler;

import hmysjiang.usefulstuffs.entity.EntityFairyLight;
import hmysjiang.usefulstuffs.network.packet.FLDead;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FLDeadHandler implements IMessageHandler<FLDead, IMessage> {
	public FLDeadHandler() {}

	@Override
	public IMessage onMessage(FLDead message, MessageContext ctx) {
		int x = message.posX;
		int y = message.posY;
		int z = message.posZ;
		
		World world = ctx.getServerHandler().playerEntity.worldObj;
		for (EntityFairyLight entity:world.getEntitiesWithinAABB(EntityFairyLight.class, new AxisAlignedBB(new BlockPos(x, y, z))))
			entity.setDead();
			
		return null;
	}

}
