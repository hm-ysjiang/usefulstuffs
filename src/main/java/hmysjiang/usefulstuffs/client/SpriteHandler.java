package hmysjiang.usefulstuffs.client;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.utils.helper.LogHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(value = Side.CLIENT)
public class SpriteHandler {
	
	public static TextureAtlasSprite lily = null;
	
	@SubscribeEvent
	public static void onPreTextureStitch(TextureStitchEvent.Pre event) {
		lily = event.getMap().registerSprite(new ResourceLocation(Reference.MOD_ID, "misc/lilypad"));
	}
	
}
