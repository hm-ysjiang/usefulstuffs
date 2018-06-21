package hmysjiang.usefulstuffs.items;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.client.gui.GUIHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class ItemBento extends Item {
	
	public ItemBento() {
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
        	if (handler.getStackInSlot(i) != null) {
        		food = handler.getStackInSlot(i);
        		food.stackSize--;
        		if (food.stackSize == 0) {
        			handler.setStackInSlot(i, null);
        		}
        		break;
        	}
        }
        
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            entityplayer.getFoodStats().addStats((ItemFood) food.getItem(), food);
            worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
            entityplayer.addStat(StatList.getObjectUseStats(this));
            entityplayer.addStat(StatList.getObjectUseStats(food.getItem()));
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
			if (handler.getStackInSlot(i) != null) {
				return false;
			}
		}
		return true;
	}

}
