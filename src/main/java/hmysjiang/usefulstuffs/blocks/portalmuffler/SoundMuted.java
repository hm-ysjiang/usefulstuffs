package hmysjiang.usefulstuffs.blocks.portalmuffler;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

public class SoundMuted implements ISound {
	private ISound sound;
	private float vol;
	
	public SoundMuted(ISound sound, float volume) {
		this.sound = sound;
		this.vol = volume;
	}

	@Override
	public ResourceLocation getSoundLocation() {
		return sound.getSoundLocation();
	}

	@Override
	public SoundEventAccessor createAccessor(SoundHandler handler) {
		return sound.createAccessor(handler);
	}

	@Override
	public Sound getSound() {
		return sound.getSound();
	}

	@Override
	public SoundCategory getCategory() {
		return sound.getCategory();
	}

	@Override
	public boolean canRepeat() {
		return sound.canRepeat();
	}

	@Override
	public int getRepeatDelay() {
		return sound.getRepeatDelay();
	}

	@Override
	public float getVolume() {
		return 0;
	}

	@Override
	public float getPitch() {
		return sound.getPitch();
	}

	@Override
	public float getXPosF() {
		return sound.getXPosF();
	}

	@Override
	public float getYPosF() {
		return sound.getYPosF();
	}

	@Override
	public float getZPosF() {
		return sound.getZPosF();
	}

	@Override
	public AttenuationType getAttenuationType() {
		return sound.getAttenuationType();
	}

}
