package hmysjiang.usefulstuffs.client.renders;

import hmysjiang.usefulstuffs.entity.projectiles.EntityLightBulb;
import hmysjiang.usefulstuffs.init.ModBlocks;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;

public class RenderLightBulb extends RenderSnowball<EntityLightBulb> {

	public RenderLightBulb(RenderManager renderManagerIn, RenderItem itemRendererIn) {
		super(renderManagerIn, Item.getItemFromBlock(ModBlocks.lightbulb), itemRendererIn);
	}

}