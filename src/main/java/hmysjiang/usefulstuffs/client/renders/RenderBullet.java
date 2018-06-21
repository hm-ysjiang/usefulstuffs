package hmysjiang.usefulstuffs.client.renders;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.entity.projectiles.EntityBullet;
import hmysjiang.usefulstuffs.items.variants.EnumBulletVariants;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBullet extends RenderArrow<EntityBullet> {

	public RenderBullet(RenderManager renderManagerIn) {
		super(renderManagerIn);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBullet entity) {
		int type = entity.getType();
		switch(type) {
		case 1:
			return new ResourceLocation(Reference.MOD_ID, "textures/entity/projectile/bullet_normal.png");
		default:
			return new ResourceLocation(Reference.MOD_ID, "textures/entity/projectile/bullet_normal.png");
		}
	}

}
