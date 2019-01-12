package hmysjiang.usefulstuffs.utils.fakeplayer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import hmysjiang.usefulstuffs.Reference;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FakePlayerHandler {
	
	public static final FakePlayerHandler INSTANCE = new FakePlayerHandler();
	private Map<Integer, List<USFakePlayer>> map;
	
	public FakePlayerHandler() {
		map = new HashMap<>();
	}
	
	public WeakReference<USFakePlayer> getFakePlayer(@Nonnull WorldServer world){
		int id = world.provider.getDimension();
		USFakePlayer new_p = new USFakePlayer(world, Reference.MOD_PROFILE);
		if (!map.containsKey(id)) {
			map.put(id, new ArrayList<USFakePlayer>());
		}
		map.get(id).add(new_p);
		return new WeakReference<USFakePlayer>(new_p);
	}
	
	public WeakReference<USFakePlayer> getFakePlayer(@Nonnull WorldServer world, double x, double y, double z, EnumFacing facing){
		int id = world.provider.getDimension();
		USFakePlayer new_p = new USFakePlayer(world, Reference.MOD_PROFILE, x, y, z, facing);
		if (!map.containsKey(id)) {
			map.put(id, new ArrayList<USFakePlayer>());
		}
		map.get(id).add(new_p);
		return new WeakReference<USFakePlayer>(new_p);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onWorldUnload(WorldEvent.Unload event) {
		if (event.getWorld() instanceof WorldServer) {
			if (map.containsKey(event.getWorld().provider.getDimension())) {
				map.get(event.getWorld().provider.getDimension()).clear();
				map.remove(event.getWorld().provider.getDimension());
			}
		}
	}
	
}
