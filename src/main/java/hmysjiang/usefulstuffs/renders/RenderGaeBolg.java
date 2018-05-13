package hmysjiang.usefulstuffs.renders;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.entity.projectiles.EntityGaeBolg;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderGaeBolg extends RenderArrow<EntityGaeBolg> {

	public RenderGaeBolg(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGaeBolg entity) {
		return new ResourceLocation("usefulstuffs:textures/entity/gaebolg.png");
	}

}
