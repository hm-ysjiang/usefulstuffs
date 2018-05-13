package hmysjiang.usefulstuffs.entity.projectiles;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import hmysjiang.usefulstuffs.init.ModDamageSources;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityGaeBolg extends EntityArrow {
	
	private @Nullable Entity victim;
	private @Nullable EntityPlayer shootingPlayer;

	public EntityGaeBolg(World worldIn) {
		super(worldIn);
		this.shootingPlayer = null;
		this.victim = null;
		this.setNoGravity(true);
	}

    public EntityGaeBolg(World worldIn, double x, double y, double z)
    {
        this(worldIn);
        this.setPosition(x, y, z);
    }

    public EntityGaeBolg(World worldIn, EntityLivingBase shooter)
    {
        this(worldIn, shooter.posX, shooter.posY + (double)shooter.getEyeHeight() - 0.10000000149011612D, shooter.posZ);
        this.shootingEntity = shooter;

        if (shooter instanceof EntityPlayer)
        {
        	this.shootingPlayer = (EntityPlayer) shooter;
            this.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
        } 
    }

	@Override
	protected ItemStack getArrowStack() {
		return null;
	}
	
	@Override
	public void setAim(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy) {
		if (shooter instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) shooter;
			Vec3d lookingVec = player.getLookVec();
			Vec3d playerPos = player.getPositionVector().addVector(0, player.eyeHeight, 0);
			Vec3d pos = new Vec3d(playerPos.xCoord, playerPos.yCoord, playerPos.zCoord);
			List<Entity> entitiesWithInAABB = new ArrayList<Entity>();
			DamageSource dmgsrc = DamageSource.causeArrowDamage(new EntityArrow(this.worldObj) {
				@Override
				protected ItemStack getArrowStack() {
					return null;
				}
			}, null);
			
			
			for (int dist = 0 ; dist<64 ; dist++) {
				pos = pos.add(lookingVec);
				if (this.worldObj.getBlockState(new BlockPos(pos)).getMaterial() != Material.AIR)
					break;
				entitiesWithInAABB = this.worldObj.getEntitiesWithinAABBExcludingEntity(shooter, new AxisAlignedBB(pos.xCoord-0.75, pos.yCoord-0.75, pos.zCoord-0.75, pos.xCoord+0.75, pos.yCoord+0.75, pos.zCoord+0.75));
				for (Entity entity:entitiesWithInAABB) {
					if (entity instanceof EntityLivingBase) {
						this.victim = entity;
						Vec3d path = this.victim.getPositionVector().subtract(playerPos).normalize();
						setThrowableHeading(path.xCoord, path.yCoord, path.zCoord, velocity, inaccuracy);
						break;
					}
				}
				if (this.victim != null)
					break;
			}
			if (this.victim == null) {
				this.setThrowableHeading(lookingVec.xCoord, lookingVec.yCoord, lookingVec.zCoord, velocity, inaccuracy);
				this.victim = null;
			}
		}
        
        this.motionX += shooter.motionX;
        this.motionZ += shooter.motionZ;
	}
	
	@Override
	protected void onHit(RayTraceResult raytraceResultIn) {
		//hit wall
		if (this.victim == null) {
			this.setDead();
			return;
		}
		//entity not vulnerable
		if (raytraceResultIn.entityHit == null || this.victim != raytraceResultIn.entityHit)
			return;
		
		
		EntityLivingBase entity = (EntityLivingBase) victim;
		entity.attackEntityFrom(ModDamageSources.dmgsrcGaeBolg, entity.getHealth());
		
		this.setDead();
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
	
	@Override
	public double getDamage() {
		return 0;
	}
	
}
