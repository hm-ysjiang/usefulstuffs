package hmysjiang.usefulstuffs.entity;

import hmysjiang.usefulstuffs.init.ModBlocks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityLightBulb extends EntityThrowable implements IProjectile {
	
	public EntityLightBulb(World worldIn) {
		super(worldIn);
	}

	public EntityLightBulb(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}
	
	public EntityLightBulb(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (!this.world.isRemote) {
			if (result.typeOfHit == Type.BLOCK) {
				Vec3d motion = new Vec3d(motionX, motionY, motionZ).normalize().scale(0.05D);
				Vec3d pos = this.getPositionVector();
				pos.subtract(motion);
				if (world.getBlockState(new BlockPos(pos)).getBlock().isReplaceable(world, new BlockPos(pos))) {
					world.setBlockState(new BlockPos(pos), ModBlocks.light_bulb.getDefaultState());
				}
				else {
					world.spawnEntity(new EntityItem(world, pos.x, pos.y, pos.z, new ItemStack(ModBlocks.light_bulb, 1)));
				}
				
				this.setDead();
			}
		}
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0.0F;
	}
	
	public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
		x*=(double)velocity;
		y*=(double)velocity;
		z*=(double)velocity;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;

        float f1 = MathHelper.sqrt(x * x + z * z);
        this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
	}

}
