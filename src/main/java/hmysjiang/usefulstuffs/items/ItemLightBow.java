package hmysjiang.usefulstuffs.items;

import java.util.List;

import javax.annotation.Nullable;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.enchantment.EnchantmentMoonLight;
import hmysjiang.usefulstuffs.entity.EntityLightArrow;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.utils.ILightChargable;
import hmysjiang.usefulstuffs.utils.helper.InventoryHelper;
import hmysjiang.usefulstuffs.utils.helper.LogHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class ItemLightBow extends ItemBow implements ILightChargable {

	public ItemLightBow() {
		setUnlocalizedName(Reference.ModItems.LIGHT_BOW.getUnlocalizedName());
		setRegistryName(Reference.ModItems.LIGHT_BOW.getRegistryName());
		setMaxStackSize(1);
		setMaxDamage(400);
		this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
			@Override
            @SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
				if (entityIn == null)
					return 0;
				ItemStack itemstack = entityIn.getActiveItemStack();
				return !itemstack.isEmpty() && itemstack.getItem() instanceof ItemLightBow ? (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F : 0;
			}
		});
        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter() {
			@Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (playerIn.capabilities.isCreativeMode || !stack.isItemDamaged()) {
			playerIn.setActiveHand(handIn);
			ActionResult<ItemStack> ret = ForgeEventFactory.onArrowNock(stack, worldIn, playerIn, handIn, true);
			if (ret != null)	return ret;
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeleft) {
		if (entityLiving instanceof EntityPlayer) {
			int duration = this.getMaxItemUseDuration(stack) - timeleft;
			int actualPullingTime = timeleft < 71980 ? 20 : 72000 - timeleft;
			ForgeEventFactory.onArrowLoose(stack, worldIn, (EntityPlayer) entityLiving, duration, true);
			if (duration < 0)	return;
			float vel = ItemBow.getArrowVelocity(duration);
			if (vel >= 0.1D) {
				EntityLightArrow arrow = new EntityLightArrow(worldIn, entityLiving);
				arrow.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0F, vel * 10F, 0.1F);
				arrow.setDamage(1.5);
				
				if (vel == 1.0F) {
					arrow.setIsCritical(true);
				}
				
				int power = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
				if (power > 0) {
					arrow.setDamage(arrow.getDamage() + (double) power * 0.5D + 0.5D);
				}
				
				stack.setItemDamage(actualPullingTime * 20);
				if (!worldIn.isRemote)
					worldIn.spawnEntity(arrow);
			}
		}
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn instanceof EntityPlayer) {
			ItemStack battery = InventoryHelper.findStackInPlayerInventoryContainBaubles((EntityPlayer) entityIn, new ItemStack(ModItems.light_battery), true);
			int charge = ItemLightBattery.getChargedEnergy(battery);
			if (charge > 0) {
				if (charge > 10)
					charge = 10;
				if (charge > stack.getItemDamage())
					charge = stack.getItemDamage();
				battery.setItemDamage(battery.getItemDamage() + charge);
				stack.setItemDamage(stack.getItemDamage() - charge);
			}
			else 
				charge(worldIn, stack, entityIn.getPosition());
		}
	}
	
	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		return 0xfffa00;
	}
	
	@Override
	public int getItemEnchantability() {
		return 3;
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab == UsefulStuffs.TAB) {
			items.add(new ItemStack(this, 1, 0));
			items.add(new ItemStack(this, 1, this.getMaxDamage()));
		}
	}
	
	@SubscribeEvent
	public static void onFOVUpdate(FOVUpdateEvent event) {
		EntityPlayer player = event.getEntity();
		if (player != null) {
			if (!player.getActiveItemStack().isEmpty() && player.getActiveItemStack().getItem() instanceof ItemLightBow) {
				float fov = event.getFov();
				int duration = 72000 - player.getItemInUseCount();
				if (duration > 40)
					duration = 40;
				float multiplier = (float) (1.0 - 0.3 * (double) duration / 40.0);
				event.setNewfov(fov * multiplier);
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("usefulstuffs.light_bow.tooltip_1"));
		tooltip.add(I18n.format("usefulstuffs.light_bow.tooltip_2"));
		tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.light_bow.tooltip_charge", String.valueOf(stack.getMaxDamage() - stack.getItemDamage())));
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.light_bow.tooltip_3"));
	}
	
}
