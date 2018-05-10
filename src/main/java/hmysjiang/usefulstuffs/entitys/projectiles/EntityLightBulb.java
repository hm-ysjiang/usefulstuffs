package hmysjiang.usefulstuffs.entitys.projectiles;

import hmysjiang.usefulstuffs.init.ModBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
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
		this.worldObj = worldIn;
	}
	
	public EntityLightBulb(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (!this.worldObj.isRemote) {
			if (result.typeOfHit == Type.BLOCK) {
				boolean placed = false;
				Vec3d motion = new Vec3d(motionX, motionY, motionZ).normalize().scale(0.1D);
				Vec3d pos = this.getPositionVector();
				do {
					pos.subtract(motion);
					if (worldObj.getBlockState(new BlockPos(pos)).getMaterial() == Material.AIR) {
						worldObj.setBlockState(new BlockPos(pos), ModBlocks.lightbulb.getDefaultState());
						placed = true;
					}
				}while(!placed);
				
				this.setDead();
			}
		}
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0.0F;
	}

}
