package hmysjiang.usefulstuffs.init;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.client.renders.*;
import hmysjiang.usefulstuffs.entity.*;
import hmysjiang.usefulstuffs.entity.projectiles.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

	public static void register() {
		EntityRegistry.registerModEntity(EntityLightBulb.class, "light_bulb", Reference.ModEntities.LIGHT_BULB.getID(), UsefulStuffs.instance, 64, 10, true);
		EntityRegistry.registerModEntity(EntityFairyLight.class, "fairy_light", Reference.ModEntities.FAIRY_LIGHT.getID(), UsefulStuffs.instance, 64, 10, true);
		EntityRegistry.registerModEntity(EntityBullet.class, "bullet", Reference.ModEntities.BULLET.getID(), UsefulStuffs.instance, 64, 10, true);
	}
	
	public static void registerRenders() {
		RenderingRegistry.registerEntityRenderingHandler(EntityLightBulb.class, new IRenderFactory() {

			@Override
			public Render createRenderFor(RenderManager manager) {
				return new RenderLightBulb(manager, Minecraft.getMinecraft().getRenderItem());
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityFairyLight.class, new IRenderFactory() {

			@Override
			public Render createRenderFor(RenderManager manager) {
				return new RenderFairyLight(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new IRenderFactory() {

			@Override
			public Render createRenderFor(RenderManager manager) {
				return new RenderBullet(manager);
			}
		});
	}
	
}