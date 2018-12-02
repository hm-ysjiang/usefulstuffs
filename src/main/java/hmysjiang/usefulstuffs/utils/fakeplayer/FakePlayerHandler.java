package hmysjiang.usefulstuffs.utils.fakeplayer;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import hmysjiang.usefulstuffs.Reference;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class FakePlayerHandler {
	
	public static final FakePlayerHandler INSTANCE = new FakePlayerHandler();
	private Map<Integer, WeakReference<FakePlayer>> map;
	
	public FakePlayerHandler() {
		map = new HashMap<>();
	}
	
	public WeakReference<FakePlayer> getFakePlayer(@Nonnull WorldServer world){
		int id = world.provider.getDimension();
		if (!map.containsKey(id)) {
			WeakReference<FakePlayer> ref = new WeakReference<FakePlayer>(FakePlayerFactory.get(world, Reference.MOD_PROFILE));
			map.put(id, ref);
			return ref;
		}
		else if (map.get(id).get() == null) {
			WeakReference<FakePlayer> ref = new WeakReference<FakePlayer>(FakePlayerFactory.get(world, Reference.MOD_PROFILE));
			map.put(id, ref);
			return ref;
		}
		return map.get(id);
	}
	
}
