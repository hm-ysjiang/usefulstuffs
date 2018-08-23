package hmysjiang.usefulstuffs.items.crafting;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Predicates;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.potion.PotionUmbrella;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class ItemUmbrella extends ItemCraftingIngredient {
	
	public static final IBehaviorDispenseItem DISPENSER_BEHAVIOR = new BehaviorDefaultDispenseItem() {
		/**
		 * Dispense the specified stack, play the dispense sound and spawn particles.
		 */
		protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
			ItemStack itemstack = ItemArmor.dispenseArmor(source, stack);
			return itemstack.isEmpty() ? super.dispenseStack(source, stack) : itemstack;
		}
	};

	public static ItemStack dispenseArmor(IBlockSource blockSource, ItemStack stack) {
		BlockPos blockpos = blockSource.getBlockPos().offset((EnumFacing)blockSource.getBlockState().getValue(BlockDispenser.FACING));
		List<EntityLivingBase> list = blockSource.getWorld().<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(blockpos), Predicates.and(EntitySelectors.NOT_SPECTATING, new EntitySelectors.ArmoredMob(stack)));

		if (list.isEmpty()) {
			return ItemStack.EMPTY;
		}
		else {
			EntityLivingBase entitylivingbase = list.get(0);
			EntityEquipmentSlot entityequipmentslot = EntityEquipmentSlot.HEAD;
			ItemStack itemstack = stack.splitStack(1);
			entitylivingbase.setItemStackToSlot(entityequipmentslot, itemstack);

			if (entitylivingbase instanceof EntityLiving) {
				((EntityLiving)entitylivingbase).setDropChance(entityequipmentslot, 2.0F);
			}

			return stack;
		}
	}

	public ItemUmbrella() {
		setUnlocalizedName(Reference.ModItems.UMBRELLA.getUnlocalizedName());
		setRegistryName(Reference.ModItems.UMBRELLA.getRegistryName());
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, DISPENSER_BEHAVIOR);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, worldIn, tooltip, advanced);
		tooltip.add(I18n.format("usefulstuffs.umbrella.tooltip", "%"));
	}
	
	@Override
	public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
		return EntityEquipmentSlot.HEAD;
	}
	
	@Override
	public void onUpdate(ItemStack stackIn, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn instanceof EntityLivingBase && ((EntityLivingBase) entityIn).getActivePotionEffect(PotionUmbrella.instance) == null) {
			ItemStack stack = ItemStack.EMPTY;
			for (Iterator itr = entityIn.getArmorInventoryList().iterator() ; itr.hasNext() ; ) {
				stack = (ItemStack) itr.next();
				if (stack.isItemEqualIgnoreDurability(stackIn)) {
					doUpdate(entityIn);
					return;
				}
			}
		}
	}

	private void doUpdate(Entity entityIn) {
		if (entityIn.fallDistance > 3)
			if (entityIn.motionY < -0.4)
				entityIn.motionY = -0.4;
	}
	
	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent event) {
		if (event.getEntityLiving().getActivePotionEffect(PotionUmbrella.instance) == null) {
			ItemStack stack = ItemStack.EMPTY;
			for (Iterator itr = event.getEntity().getArmorInventoryList().iterator() ; itr.hasNext() ; ) {
				stack = (ItemStack) itr.next();
				if (stack.isItemEqualIgnoreDurability(new ItemStack(ModItems.umbrella))) {
					event.setDamageMultiplier(event.getDamageMultiplier() * 0.7F);
					return;
				}
			}
		}
	}
	
}
