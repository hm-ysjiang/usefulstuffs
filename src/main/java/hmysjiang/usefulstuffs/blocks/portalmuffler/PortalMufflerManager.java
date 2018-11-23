package hmysjiang.usefulstuffs.blocks.portalmuffler;

import java.util.ArrayList;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.utils.SoundMuted;
import hmysjiang.usefulstuffs.utils.TileEntityManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PortalMufflerManager extends TileEntityManager<TileEntityPortalMuffler> {
	public static final ResourceLocation SOUND_PORTAL = new ResourceLocation("minecraft:block.portal.ambient");
	public static final PortalMufflerManager INSTANCE = new PortalMufflerManager();
	
	public static int rangeSq;
	
	public static boolean isPosInRange(BlockPos pos1, BlockPos pos2) {
		if (pos1 == null || pos2 == null) return false;
		return pos1.distanceSq(pos2.getX(), pos2.getY(), pos2.getZ()) <= rangeSq;
	}
	
	public PortalMufflerManager() {
		rangeSq = ConfigManager.portalMufflerRange * ConfigManager.portalMufflerRange;
	}
	
	@Override
	protected Side getSide() {
		return Side.CLIENT;
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onSoundPlay(PlaySoundEvent event) {
		if (clients == null)
			clients = new ArrayList<TileEntityPortalMuffler>();
		WorldClient world = Minecraft.getMinecraft().world;
		if (world != null) {
			ISound sound = event.getSound();
			if (sound.getSoundLocation().equals(SOUND_PORTAL)) {
				BlockPos soundPos = new BlockPos(sound.getXPosF(), sound.getYPosF(), sound.getZPosF());
				for (TileEntityPortalMuffler tile: clients) {
					if (!world.isAreaLoaded(tile.getPos(), 1)) continue;
					if (world.provider.getDimension() == tile.getWorld().provider.getDimension() && isPosInRange(tile.getPos(), soundPos)) {
						event.setResultSound(new SoundMuted(sound, 0));
						break;
					}
				}
			}
		}
	}
	
}
