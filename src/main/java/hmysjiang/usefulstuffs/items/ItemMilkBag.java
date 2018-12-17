package hmysjiang.usefulstuffs.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMilkBag extends Item {
	
	public static List<Potion> negativeEffects = new ArrayList<Potion>();
	public static void setDefaultTag(ItemStack stack) {
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("FermentLevel", 0);
	}
	
	public static boolean isCompletelyFermented(ItemStack stack) {
		if (!stack.hasTagCompound()) return false;
		if (stack.getItemDamage() == 8) return false;
		return stack.getTagCompound().getInteger("FermentLevel") >= 100 ? true : false;
	}
	
	static {
		negativeEffects.add(MobEffects.BLINDNESS);
		negativeEffects.add(MobEffects.NAUSEA);
		negativeEffects.add(MobEffects.POISON);
		negativeEffects.add(MobEffects.SLOWNESS);
		negativeEffects.add(MobEffects.WEAKNESS);
	}
	
	private Random rnd;
	
	public ItemMilkBag() {
		setUnlocalizedName(Reference.ModItems.MILK_BAG.getUnlocalizedName());
		setRegistryName(Reference.ModItems.MILK_BAG.getRegistryName());
		setMaxStackSize(1);
		setMaxDamage(8);
		setNoRepair();
		
		rnd = new Random();
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (stack.hasTagCompound() && isCompletelyFermented(stack)) {
			stack.setItemDamage(stack.getItemDamage() + 1);
			playerIn.addItemStackToInventory(new ItemStack(ModItems.cheese));
			if (stack.getItemDamage() == 8)
				stack.getTagCompound().setInteger("FermentLevel", 0);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		if (playerIn.isSneaking() || stack.getItemDamage() == 8)
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
		playerIn.setActiveHand(handIn);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("FermentLevel")) {
			setDefaultTag(stack);
		}
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target,
			EnumHand hand) {
		if (stack.hasTagCompound() && stack.getTagCompound().getInteger("FermentLevel") > 0) return false;
		if (playerIn.isSneaking() && target.getClass().equals(EntityCow.class)) {
			if (playerIn.world.isRemote)
				playerIn.world.playSound(playerIn, playerIn.getPosition(), SoundEvents.ITEM_BUCKET_FILL, SoundCategory.PLAYERS, 1.0F, 1.0F);
			else
				stack.setItemDamage(stack.getItemDamage() - 1);
			return true;
		}
		return super.itemInteractionForEntity(stack, playerIn, target, hand);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if (!worldIn.isRemote) {
			if (!isCompletelyFermented(stack)) {
				if (ConfigManager.fermentedMilkCauseNegativeEffect) {
					int fermentLevel = (int) (stack.getTagCompound().getInteger("FermentLevel") * 0.8F);
					if (rnd.nextInt(100) < fermentLevel) {
						entityLiving.addPotionEffect(new PotionEffect(negativeEffects.get(rnd.nextInt(5)), 600));
					}
					else {
						entityLiving.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
					}
				}
				else {
					entityLiving.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
				}
			}
		}
		stack.setItemDamage(stack.getItemDamage() + 1);
		if (stack.getItemDamage() == 8 && stack.hasTagCompound())
			stack.getTagCompound().setInteger("FermentLevel", 0);
		return stack;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab == UsefulStuffs.TAB) {
			items.add(new ItemStack(this, 1, 0));
			items.add(new ItemStack(this, 1, 8));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getItemDamage() == 8)
			return super.getUnlocalizedName() + "_empty";
		if (stack.hasTagCompound() && stack.getTagCompound().getInteger("FermentLevel") > 0) {
			if (isCompletelyFermented(stack))
				return super.getUnlocalizedName() + "_fermented";
			return super.getUnlocalizedName() + "_fermented_par";
		}
		return super.getUnlocalizedName(stack);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.getItemDamage() < 7) {
			tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.milk_bag.tooltip_1", (8 - stack.getItemDamage())));
		}
		else {
			tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.milk_bag.tooltip_1_1", (8 - stack.getItemDamage())));
		}
		if (stack.hasTagCompound() && stack.getTagCompound().getInteger("FermentLevel") > 0)
			tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.milk_bag.tooltip_2", (String.valueOf(stack.getTagCompound().getInteger("FermentLevel")) + "%")));
		tooltip.add(I18n.format("usefulstuffs.milk_bag.tooltip_3"));
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.milk_bag.tooltip_4"));
	}
	
}
