package hmysjiang.usefulstuffs.entity.projectiles;

import hmysjiang.usefulstuffs.init.ModBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
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
		if (!this.worldObj.isRemote) {
			if (result.typeOfHit == Type.BLOCK) {
				Vec3d motion = new Vec3d(motionX, motionY, motionZ).normalize().scale(0.05D);
				Vec3d pos = this.getPositionVector();
				pos.subtract(motion);
				if (worldObj.getBlockState(new BlockPos(pos)).getBlock().isReplaceable(worldObj, new BlockPos(pos))) {
					worldObj.setBlockState(new BlockPos(pos), ModBlocks.lightbulb.getDefaultState());
				}
				else {
					worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.xCoord, pos.yCoord, pos.zCoord, new ItemStack(ModBlocks.lightbulb, 1)));
				}
				
				this.setDead();
			}
		}
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0.0F;
	}
	
	@Override
	public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
		x*=(double)velocity;
		y*=(double)velocity;
		z*=(double)velocity;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;

        float f1 = MathHelper.sqrt_double(x * x + z * z);
        this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
	}

}
