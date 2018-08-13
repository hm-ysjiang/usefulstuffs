package hmysjiang.usefulstuffs.blocks.portalmuffler;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PortalMufflerManager {
	public static final ResourceLocation SOUND_PORTAL = new ResourceLocation("minecraft:block.portal.ambient");
	public static final PortalMufflerManager INSTANCE = new PortalMufflerManager();
	
	public static boolean isPosInRange(BlockPos pos1, BlockPos pos2) {
		if (pos1 == null || pos2 == null) return false;
		return pos1.distanceSq(pos2.getX(), pos2.getY(), pos2.getZ()) <= 25;
	}
	
	private List<TileEntityPortalMuffler> mufflers;
	
	public PortalMufflerManager() {
		mufflers = new ArrayList<TileEntityPortalMuffler>();
	}
	
	@SubscribeEvent
	public void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (mufflers == null)
			mufflers = new ArrayList<TileEntityPortalMuffler>();
		mufflers.clear();
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onSoundPlay(PlaySoundEvent event) {
		if (mufflers == null)
			mufflers = new ArrayList<TileEntityPortalMuffler>();
		WorldClient world = Minecraft.getMinecraft().world;
		if (world != null) {
			ISound sound = event.getSound();
			if (sound.getSoundLocation().equals(SOUND_PORTAL)) {
				BlockPos soundPos = new BlockPos(sound.getXPosF(), sound.getYPosF(), sound.getZPosF());
				for (TileEntityPortalMuffler tile: mufflers) {
					if (world.provider.getDimension() == tile.getWorld().provider.getDimension() && isPosInRange(tile.getPos(), soundPos)) {
						event.setResultSound(new SoundMuted(sound, 0));
						break;
					}
				}
			}
		}
	}
	
	//Returns true if successfully registered
	public boolean registerMuffler(TileEntityPortalMuffler muffler) {
		if (muffler == null) return false;
		for (TileEntityPortalMuffler muffler1: mufflers) 
			if (muffler1.getWorld().provider.getDimension() == muffler.getWorld().provider.getDimension())
				if (muffler1.getPos().equals(muffler.getPos()))
					return true;
		mufflers.add(muffler);
		return true;
	}
	
	public void unregisterMuffler(TileEntityPortalMuffler muffler) {
		for (int i = 0 ; i<mufflers.size() ; i++) 
			if (mufflers.get(i).getWorld().provider.getDimension() == muffler.getWorld().provider.getDimension())
				if (mufflers.get(i).getPos().equals(muffler.getPos()))
					mufflers.remove(i);
	}
	
}
