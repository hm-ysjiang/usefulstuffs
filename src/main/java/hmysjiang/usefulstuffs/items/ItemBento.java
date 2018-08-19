package hmysjiang.usefulstuffs.items;

import java.util.List;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.client.gui.GuiHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public class ItemBento extends ItemFood {
	
	public static boolean speed;
	
	public ItemBento() {
		super(0, 0, false);
		setUnlocalizedName(Reference.ModItems.BENTO.getUnlocalizedName());
		setRegistryName(Reference.ModItems.BENTO.getRegistryName());
		setMaxStackSize(1);
		speed = ConfigManager.bentoSpeed;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Cont")) {
			ItemStackHandler handler = new ItemStackHandler(6);
			NBTTagCompound compound = new NBTTagCompound();
			compound.setTag("Cont", handler.serializeNBT());
			stack.setTagCompound(compound);
		}
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		ItemStack food = getNextFood(stack);
		return food.isEmpty() ? 0 : (speed ? ((ItemFood)food.getItem()).getMaxItemUseDuration(food) : 32);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		ItemStack food = getNextFood(stack);
		return food.isEmpty() ? EnumAction.NONE : ((ItemFood)food.getItem()).getItemUseAction(food);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
		if (!playerIn.world.isRemote && hand == EnumHand.MAIN_HAND && playerIn.isSneaking()) {
			ItemStack stack = playerIn.getHeldItem(hand);
			playerIn.openGui(UsefulStuffs.instance, GuiHandler.GUI_BENTO, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		else {
			ItemStack stack = playerIn.getHeldItem(hand);
			if (playerIn.canEat(false) && !isEmpty(stack))
			{
				playerIn.setActiveHand(hand);
				return new ActionResult(EnumActionResult.SUCCESS, stack);
			}
			else
			{
				return new ActionResult(EnumActionResult.FAIL, stack);
			}
		}
		
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Cont")) {
			return stack;
		}
		ItemStack food = getNextFood(stack);
		if (!food.isEmpty()) {
			consumeOne(stack, worldIn, entityLiving);
		}
		if (entityLiving instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer)entityLiving;
			entityplayer.addStat(StatList.getObjectUseStats(this));
			entityplayer.addStat(StatList.getObjectUseStats(food.getItem()));
		}
		return stack;
	}
	
	public ItemStack getNextFood(ItemStack stack) {
		if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Cont")) {
			return ItemStack.EMPTY;
		}
		ItemStackHandler handler = new ItemStackHandler(6);
		handler.deserializeNBT(stack.getTagCompound().getCompoundTag("Cont"));
		for (int i = 0 ; i<handler.getSlots() ; i++) {
			if (!handler.getStackInSlot(i).isEmpty() && handler.getStackInSlot(i).getItem() instanceof ItemFood) {
				return handler.getStackInSlot(i);
			}
		}
		return ItemStack.EMPTY;
	}
	
	public void consumeOne(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		ItemStack food = ItemStack.EMPTY;
		ItemStackHandler handler = new ItemStackHandler(6);
		handler.deserializeNBT(stack.getTagCompound().getCompoundTag("Cont"));
		for (int i = 0 ; i<handler.getSlots() ; i++) {
			if (!handler.getStackInSlot(i).isEmpty() && handler.getStackInSlot(i).getItem() instanceof ItemFood) {
				food = handler.getStackInSlot(i);
				food = food.onItemUseFinish(worldIn, entityLiving);
				handler.setStackInSlot(i, food.getCount() == 0 ? ItemStack.EMPTY : food);
				break;
			}
		}
		NBTTagCompound compound = stack.getTagCompound();
		compound.setTag("Cont", handler.serializeNBT());
		stack.setTagCompound(compound);
	}
	
	public boolean isEmpty(ItemStack stack) {
		return getNextFood(stack) == null;
	}
	
	@Override
	public int getHealAmount(ItemStack stack) {
		ItemStack food = getNextFood(stack);
		return food.isEmpty() ? 0 : ((ItemFood)food.getItem()).getHealAmount(food);
	}
	
	@Override
	public float getSaturationModifier(ItemStack stack) {
		ItemStack food = getNextFood(stack);
		return food.isEmpty() ? 0 : ((ItemFood)food.getItem()).getSaturationModifier(food);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(I18n.format("usefulstuffs.bento.tooltip_1"));
		tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.bento.tooltip_2"));
	}

}
