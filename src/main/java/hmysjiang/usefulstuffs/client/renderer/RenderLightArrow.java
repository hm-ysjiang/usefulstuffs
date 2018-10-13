package hmysjiang.usefulstuffs.client.renderer;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.entity.EntityLightArrow;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderLightArrow extends RenderArrow<EntityLightArrow> {
	
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/entity/projectile/light_arrow.png");

	public RenderLightArrow(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLightArrow entity) {
		return TEXTURE;
	}

}
