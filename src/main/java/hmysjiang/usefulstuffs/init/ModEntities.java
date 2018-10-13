package hmysjiang.usefulstuffs.init;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.client.renderer.RenderLightArrow;
import hmysjiang.usefulstuffs.client.renderer.RenderLightBulb;
import hmysjiang.usefulstuffs.entity.EntityLightArrow;
import hmysjiang.usefulstuffs.entity.EntityLightBulb;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

	public static void register() {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "light_bulb"), EntityLightBulb.class, "light_bulb", Reference.ModEntities.LIGHT_BULB.getID(), UsefulStuffs.instance, 64, 10, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "light_arrow"), EntityLightArrow.class, "light_arrow", Reference.ModEntities.LIGHT_ARROW.getID(), UsefulStuffs.instance, 64, 10, true);
	}
	
	public static void registerRenders() {
		RenderingRegistry.registerEntityRenderingHandler(EntityLightBulb.class, new IRenderFactory() {

			@Override
			public Render createRenderFor(RenderManager manager) {
				return new RenderLightBulb(manager, Minecraft.getMinecraft().getRenderItem());
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityLightArrow.class, new IRenderFactory() {

			@Override
			public Render createRenderFor(RenderManager manager) {
				return new RenderLightArrow(manager);
			}
		});
	}
	
}
