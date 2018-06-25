package hmysjiang.usefulstuffs.event.handler;

import hmysjiang.usefulstuffs.Reference;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpriteHandler {
	
	public static final SpriteHandler instance = new SpriteHandler();
	public static final ResourceLocation textureLily = new ResourceLocation(Reference.MOD_ID, "miscs/lilypad");
	
	@SubscribeEvent
	public void onPreTextureStitch(TextureStitchEvent.Pre event) {
		event.getMap().registerSprite(textureLily);
	}
	
}
