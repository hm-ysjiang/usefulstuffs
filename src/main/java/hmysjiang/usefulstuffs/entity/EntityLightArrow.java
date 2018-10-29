package hmysjiang.usefulstuffs.entity;

import hmysjiang.usefulstuffs.network.PacketHandler;
import hmysjiang.usefulstuffs.network.packet.SyncLightArrow;
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
		if (this.inGround) 
			this.setDead();
		if (!this.world.isRemote)
			PacketHandler.INSTANCE.sendToDimension(new SyncLightArrow(this), this.world.provider.getDimension());
		super.onUpdate();
		if (this.world.isRemote) {
			this.spawnParticles();
		}
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

}
