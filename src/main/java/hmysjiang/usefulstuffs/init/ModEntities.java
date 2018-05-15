package hmysjiang.usefulstuffs.init;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.entity.*;
import hmysjiang.usefulstuffs.entity.projectiles.*;
import hmysjiang.usefulstuffs.renders.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

	public static void register() {
		EntityRegistry.registerModEntity(EntityLightBulb.class, I18n.format("entity.light_bulb.name"), Reference.ModEntities.LIGHT_BULB.getID(), UsefulStuffs.instance, 64, 10, true);
		
	}
	
	public static void registerRenders() {
		RenderingRegistry.registerEntityRenderingHandler(EntityLightBulb.class, new IRenderFactory() {

			@Override
			public Render createRenderFor(RenderManager manager) {
				return new RenderLightBulb(manager, Minecraft.getMinecraft().getRenderItem());
			}
		});
	}
	
}