package hmysjiang.usefulstuffs.items;

import javax.annotation.Nullable;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.enchantment.EnchantmentMoonLight;
import hmysjiang.usefulstuffs.entity.EntityLightArrow;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLightBow extends ItemBow {

	public ItemLightBow() {
		setUnlocalizedName(Reference.ModItems.LIGHT_BOW.getUnlocalizedName());
		setRegistryName(Reference.ModItems.LIGHT_BOW.getRegistryName());
		setMaxStackSize(1);
		setMaxDamage(401);
		this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
			
			@Override
			public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
				if (entityIn == null)
					return 0;
				ItemStack itemstack = entityIn.getActiveItemStack();
				return !itemstack.isEmpty() && itemstack.getItem() instanceof ItemLightBow ? (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F : 0;
			}
		});
        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (playerIn.capabilities.isCreativeMode) {
			playerIn.setActiveHand(handIn);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		if (stack.getItemDamage() <= 1) {
			playerIn.setActiveHand(handIn);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeleft) {
		if (entityLiving instanceof EntityPlayer) {
			int duration = this.getMaxItemUseDuration(stack) - timeleft;
			int actualPullingTime = timeleft < 71980 ? 20 : 72000 - timeleft;
			if (duration < 0)	return;
			float vel = ItemBow.getArrowVelocity(duration);
			if (!worldIn.isRemote && vel >= 0.1D) {
				EntityLightArrow arrow = new EntityLightArrow(worldIn, entityLiving);
				arrow.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0F, vel * 5.0F, 0.5F);
				
				if (vel == 1.0F) {
					arrow.setIsCritical(true);
				}
				
				int power = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
				if (power > 0) {
					arrow.setDamage(arrow.getDamage() + (double) power * 0.5D + 0.5D);
				}
				stack.setItemDamage(actualPullingTime * 20 + 1);
				worldIn.spawnEntity(arrow);
			}
		}
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (worldIn.isRemote)	return;
		if (!worldIn.canBlockSeeSky(entityIn.getPosition()) || worldIn.isRaining()) return;
		int time = (int) worldIn.getWorldTime();
		int charge = getChargeAmount(time, EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0, EnchantmentHelper.getEnchantmentLevel(EnchantmentMoonLight.INSTANCE, stack) > 0);
		if (stack.getItemDamage() < 1) {
			stack.setItemDamage(1);
		}
		if (stack.getItemDamage() > 1) {
			stack.setItemDamage(stack.getItemDamage() - 1 > charge ? stack.getItemDamage() - charge : 1);
		}
	}
	
	public static int getChargeAmount(int time, boolean infinity, boolean moonlight) {
		if (infinity) {
			return 4;
		}
		if (time <= 12000) {
			int dif = MathHelper.abs(6000 - time);
			int charge =  (int) (MathHelper.cos((float) (Math.PI * dif / 12000)) * 4 + 1);
			return charge;
		}
		if (moonlight) {
			int aug_time = time - 12000;
			int dif = MathHelper.abs(6000 - aug_time);
			int charge =  (int) (MathHelper.cos((float) (Math.PI * dif / 12000)) * 2 + 1);
			return charge;
		}
		return 0;
	}
	
	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		return 0xfffa00;
	}
	
	@Override
	public int getItemEnchantability() {
		return 3;
	}
	
}
