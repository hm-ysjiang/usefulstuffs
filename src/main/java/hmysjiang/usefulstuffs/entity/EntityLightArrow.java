package hmysjiang.usefulstuffs.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityLightArrow extends EntityArrow {

	public EntityLightArrow(World worldIn) {
		super(worldIn);
	}
	
	public EntityLightArrow(World world, double x, double y, double z) {
		super(world, x, y, z);
	}
	
	public EntityLightArrow(World world, EntityLivingBase shooter) {
		super(world, shooter);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.inGround) {
			this.setDead();
		}
		else if (this.world.isRemote) {
			this.spawnParticles();
		}
//		else {
//			world.update
//		}
	}
	
	private void spawnParticles() {
		int hexColor = 0xfffb19;
		double r = (double)(hexColor >> 16 & 255) / 255.0D;
		double g = (double)(hexColor >> 8 & 255) / 255.0D;
		double b = (double)(hexColor >> 0 & 255) / 255.0D;
		for (int j = 0; j < 3; j++) {
			this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, r, g, b);
		}
	}

	@Override
	protected void onHit(RayTraceResult raytraceResultIn) {
		super.onHit(raytraceResultIn);
		this.setDead();
	}

	@Override
	protected ItemStack getArrowStack() {
		return ItemStack.EMPTY;
	}
	
	@Override
	protected void arrowHit(EntityLivingBase living) {
		super.arrowHit(living);
		this.setDead();
	}

}
