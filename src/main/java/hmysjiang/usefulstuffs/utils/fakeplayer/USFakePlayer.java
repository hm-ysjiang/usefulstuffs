package hmysjiang.usefulstuffs.utils.fakeplayer;

import com.mojang.authlib.GameProfile;

import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

public class USFakePlayer extends FakePlayer {

	public USFakePlayer(WorldServer world, GameProfile name) {
		super(world, name);
	}

}
