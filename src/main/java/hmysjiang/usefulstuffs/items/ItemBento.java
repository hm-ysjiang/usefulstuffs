package hmysjiang.usefulstuffs.items;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.client.gui.GUIHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraftforge.items.ItemStackHandler;

public class ItemBento extends ItemFood {
	
	public ItemBento() {
		super(0, 0, false);
		setUnlocalizedName(Reference.ModItems.BENTO.getUnlocalizedName());
		setRegistryName(Reference.ModItems.BENTO.getRegistryName());
		setMaxStackSize(1);
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
		return 32;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.EAT;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
		if (!playerIn.worldObj.isRemote && hand == EnumHand.MAIN_HAND && playerIn.isSneaking()) {
			playerIn.openGui(UsefulStuffs.instance, GUIHandler.GUI_BENTO, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
		}
		else {
			if (playerIn.canEat(false) && !isEmpty(itemStackIn))
	        {
	            playerIn.setActiveHand(hand);
	            return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
	        }
	        else
	        {
	            return new ActionResult(EnumActionResult.FAIL, itemStackIn);
	        }
		}
		
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Cont")) {
        	return stack;
        }
        ItemStack food = null;
        ItemStackHandler handler = new ItemStackHandler(6);
        handler.deserializeNBT(stack.getTagCompound().getCompoundTag("Cont"));
        for (int i = 0 ; i<handler.getSlots() ; i++) {
        	if (handler.getStackInSlot(i) != null && handler.getStackInSlot(i).getItem() instanceof ItemFood) {
        		food = handler.getStackInSlot(i);
        		food = food.onItemUseFinish(worldIn, entityLiving);
        		handler.setStackInSlot(i, food.stackSize == 0 ? null : food);
        		break;
        	}
        }
        
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            entityplayer.addStat(StatList.getObjectUseStats(this));
        }
        
        NBTTagCompound compound = stack.getTagCompound();
        compound.setTag("Cont", handler.serializeNBT());
        stack.setTagCompound(compound);
		return stack;
	}
	
	public boolean isEmpty(ItemStack stack) {
		ItemStackHandler handler = new ItemStackHandler(6);
		handler.deserializeNBT(stack.getTagCompound().getCompoundTag("Cont"));
		for (int i = 0 ; i<handler.getSlots() ; i++) {
			if (handler.getStackInSlot(i) != null && handler.getStackInSlot(i).getItem() instanceof ItemFood) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(I18n.format("usefulstuffs.bento.tooltip_1"));
		tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.bento.tooltip_2"));
	}
	
	@Override
	public int getHealAmount(ItemStack stack) {
		if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Cont")) {
        	return 0;
        }
        ItemStack food = null;
        ItemStackHandler handler = new ItemStackHandler(6);
        handler.deserializeNBT(stack.getTagCompound().getCompoundTag("Cont"));
        for (int i = 0 ; i<handler.getSlots() ; i++) {
        	if (handler.getStackInSlot(i) != null && handler.getStackInSlot(i).getItem() instanceof ItemFood) {
        		food = handler.getStackInSlot(i);
        		return ((ItemFood)food.getItem()).getHealAmount(food);
        	}
        }
		return super.getHealAmount(stack);
	}
	
	@Override
	public float getSaturationModifier(ItemStack stack) {
		if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Cont")) {
        	return 0;
        }
        ItemStack food = null;
        ItemStackHandler handler = new ItemStackHandler(6);
        handler.deserializeNBT(stack.getTagCompound().getCompoundTag("Cont"));
        for (int i = 0 ; i<handler.getSlots() ; i++) {
        	if (handler.getStackInSlot(i) != null && handler.getStackInSlot(i).getItem() instanceof ItemFood) {
        		food = handler.getStackInSlot(i);
        		return ((ItemFood)food.getItem()).getSaturationModifier(food);
        	}
        }
		return super.getSaturationModifier(stack);
	}

}
