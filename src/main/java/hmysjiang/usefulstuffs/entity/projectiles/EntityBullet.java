package hmysjiang.usefulstuffs.entity.projectiles;

import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.items.variants.EnumBulletVariants;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityBullet extends EntityArrow implements IProjectile {
	
	private int type;

	public EntityBullet(World worldIn) {
		super(worldIn);
	}

    public EntityBullet(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public EntityBullet(World worldIn, EntityLivingBase shooter)
    {
        super(worldIn, shooter);
    }

	@Override
	protected ItemStack getArrowStack() {
		return null;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	
	@Override
	protected void onHit(RayTraceResult raytraceResultIn) {
		Entity entity = raytraceResultIn.entityHit;

        if (entity != null)
        {
            float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            int i = MathHelper.ceiling_double_int((double)f * this.getDamage());

            if (this.getIsCritical())
            {
                i += this.rand.nextInt(i / 2 + 2);
            }

            DamageSource damagesource = new EntityDamageSourceIndirect("bullet_" + EnumBulletVariants.values()[type].getName(), this, (shootingEntity == null ? this : shootingEntity));

            if (this.isBurning() && !(entity instanceof EntityEnderman))
            {
                entity.setFire(5);
            }

            if (entity.attackEntityFrom(damagesource, (float)i))
            {
                if (entity instanceof EntityLivingBase)
                {
                    EntityLivingBase entitylivingbase = (EntityLivingBase)entity;

                    if (this.shootingEntity instanceof EntityLivingBase)
                    {
                        EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
                        EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)this.shootingEntity, entitylivingbase);
                    }

                    this.arrowHit(entitylivingbase);
                }

                if (!(entity instanceof EntityEnderman))
                {
                    this.setDead();
                }
            }
            else
            {
                this.motionX *= -0.10000000149011612D;
                this.motionY *= -0.10000000149011612D;
                this.motionZ *= -0.10000000149011612D;
                this.rotationYaw += 180.0F;
                this.prevRotationYaw += 180.0F;
            }
        }
        else {
        	this.setDead();
        }
		
//		super.onHit(raytraceResultIn);
	}

}
