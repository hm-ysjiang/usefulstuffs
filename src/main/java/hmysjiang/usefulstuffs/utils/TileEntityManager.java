package hmysjiang.usefulstuffs.utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;

public abstract class TileEntityManager<T extends TileEntityManagerClient> {
	
	protected List<T> clients;
	
	public TileEntityManager() {
		clients = new ArrayList<T>();
	}
	
	/***
	 * This specify which side the handler should be running on.
	 * 
	 * Return this properly and remember to register the instance to the event bus on the right side
	 */
	@Nonnull
	protected abstract Side getSide();
	
	@SubscribeEvent
	public void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (clients == null)
			clients = new ArrayList<T>();
		clients.clear();
	}
	
	//Returns true if successfully registered
	public boolean registerClient(T client) {
		if (client == null) return false;
		for (T client1: clients) 
			if (client1.getWorld().provider.getDimension() == client.getWorld().provider.getDimension())
				if (client1.getPos().equals(client.getPos()))
					return true;
		clients.add(client);
		return true;
	}
	
	public void unregisterClient(T client) {
		for (int i = 0 ; i<clients.size() ; i++) 
			if (clients.get(i).getWorld().provider.getDimension() == client.getWorld().provider.getDimension())
				if (clients.get(i).getPos().equals(client.getPos()))
					clients.remove(i);
	}
	
	protected boolean onCorrectSide(World world) {
		if (getSide() == null)
			return false;
		return world.isRemote ^ getSide() == Side.SERVER;
	}

}
