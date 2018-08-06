package hmysjiang.usefulstuffs.client.renderer;

import hmysjiang.usefulstuffs.entity.EntityLightBulb;
import hmysjiang.usefulstuffs.init.ModBlocks;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;

public class RenderLightBulb extends RenderSnowball<EntityLightBulb> {

	public RenderLightBulb(RenderManager renderManagerIn, RenderItem itemRendererIn) {
		super(renderManagerIn, Item.getItemFromBlock(ModBlocks.light_bulb), itemRendererIn);
	}

}