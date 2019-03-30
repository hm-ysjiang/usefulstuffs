package hmysjiang.usefulstuffs.enchantment;

import javax.annotation.Nullable;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.Reference;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class EnchantmentEndest extends Enchantment {

	public static final EnchantmentEndest INSTANCE = new EnchantmentEndest();
	private static final String TAG = "usefulstuffs_endest";
	
	protected EnchantmentEndest() {
		super(Rarity.UNCOMMON, EnumEnchantmentType.BOW, EntityEquipmentSlot.values());
		setRegistryName(new ResourceLocation(Reference.MOD_ID, "endest"));
		setName("usefulstuffs.endest.name");
	}
	
    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 10 + 20 * (enchantmentLevel - 1);
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }
	
	@Override
	public int getMaxLevel() {
		return 1;
	}
	
	@SubscribeEvent
	public static void onArrowSpawn(EntityJoinWorldEvent event) {
		if (ConfigManager.functionEndest && !event.getWorld().isRemote && event.getEntity() instanceof EntityArrow && ((EntityArrow)event.getEntity()).shootingEntity != null && ((EntityArrow)event.getEntity()).shootingEntity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) ((EntityArrow)event.getEntity()).shootingEntity;
			EntityArrow arrow = (EntityArrow)event.getEntity();
			ItemStack stack = player.getActiveItemStack();
			if (EnchantmentHelper.getEnchantmentLevel(INSTANCE, stack) > 0) {
				arrow.addTag(TAG);
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.LOW)
	public static void onArrowImpact(ProjectileImpactEvent.Arrow event) {
		if (ConfigManager.functionEndest && event.getArrow().getTags().contains(TAG) && event.getRayTraceResult().entityHit != null && event.getRayTraceResult().entityHit instanceof EntityEnderman) {
			EntityArrow arrow = event.getArrow();
			float f = MathHelper.sqrt(arrow.motionX * arrow.motionX + arrow.motionY * arrow.motionY + arrow.motionZ * arrow.motionZ);
			int i = MathHelper.ceil((double)f * arrow.getDamage());
			event.getRayTraceResult().entityHit.attackEntityFrom((new DamageSourceEndest("arrow", arrow, arrow.shootingEntity)).setProjectile(), (float) i);
			arrow.setDead();
			event.setCanceled(true);
		}
	}
	
	public static class DamageSourceEndest extends EntityDamageSource {
		
		private final Entity indirectEntity;

		public DamageSourceEndest(String damageTypeIn, Entity source, @Nullable Entity indirectEntityIn) {
			super(damageTypeIn, source);
			this.indirectEntity = indirectEntityIn;
		}

		@Nullable
		public Entity getImmediateSource() {
			return this.damageSourceEntity;
		}

		@Nullable
		public Entity getTrueSource() {
			return this.indirectEntity;
		}

		public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
			ITextComponent itextcomponent = this.indirectEntity == null ? this.damageSourceEntity.getDisplayName() : this.indirectEntity.getDisplayName();
			ItemStack itemstack = this.indirectEntity instanceof EntityLivingBase ? ((EntityLivingBase)this.indirectEntity).getHeldItemMainhand() : ItemStack.EMPTY;
			String s = "death.attack." + this.damageType;
			String s1 = s + ".item";
			return !itemstack.isEmpty() && itemstack.hasDisplayName() && I18n.canTranslate(s1) ? new TextComponentTranslation(s1, new Object[] {entityLivingBaseIn.getDisplayName(), itextcomponent, itemstack.getTextComponent()}) : new TextComponentTranslation(s, new Object[] {entityLivingBaseIn.getDisplayName(), itextcomponent});
		}
	}

}
