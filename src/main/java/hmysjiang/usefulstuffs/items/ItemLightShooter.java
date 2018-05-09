package hmysjiang.usefulstuffs.items;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.entitys.projectiles.EntityLightBulb;
import hmysjiang.usefulstuffs.init.ModBlocks;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemLightShooter extends Item {
	
	private final int FULL_COOLDOWN = 30;
	private final int MAX_AMMO = 256;
	private final String KEY_AMMO = "Ammo";
	
	private int ammoStored;
	private int cd;
	
	public ItemLightShooter() {
		setUnlocalizedName(Reference.ModItems.LIGHT_SHOOTER.getUnlocalizedName());
		setRegistryName(Reference.ModItems.LIGHT_SHOOTER.getRegistryName());
		
		cd = 0;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(KEY_AMMO)) {
			tooltip.add(I18n.format("light_shooter.tooltip", String.valueOf(stack.getTagCompound().getInteger(KEY_AMMO)), String.valueOf(this.MAX_AMMO)));
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
		if (!worldIn.isRemote) {
			//charge
			if (playerIn.isSneaking()) {
				this.addAmmo(playerIn, itemStackIn);
			}
			//launch
			else if (cd == 0 && itemStackIn.hasTagCompound() && itemStackIn.getTagCompound().hasKey(KEY_AMMO)) {
				this.launchAmmo(itemStackIn, worldIn, playerIn);
				this.cd = FULL_COOLDOWN;
			}
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}
	
	private void launchAmmo(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		NBTTagCompound nbtcompound = itemStackIn.getTagCompound();
		if (nbtcompound.getInteger(KEY_AMMO) > 0) {
			EntityLightBulb entity = new EntityLightBulb(worldIn, playerIn);
	        entity.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
	        worldIn.spawnEntityInWorld(entity);
	        
	        nbtcompound.setInteger(KEY_AMMO, nbtcompound.getInteger(KEY_AMMO)-1);
	        itemStackIn.setTagCompound(nbtcompound);
		}
	}

	private void addAmmo(EntityPlayer playerIn, ItemStack itemStackIn) {
        for (ItemStack itemstack : playerIn.inventory.mainInventory) {
        	if (ammoStored == MAX_AMMO)
        		break; 
            if (itemstack != null && itemstack.isItemEqual(new ItemStack(ModBlocks.lightbulb))) {
            	//to be fixed
            	--itemstack.stackSize;
                NBTTagCompound nbtcompound;
                if (itemStackIn.hasTagCompound()) 
                	nbtcompound = itemStackIn.getTagCompound();
                else nbtcompound = new NBTTagCompound();
                
                if (nbtcompound.hasKey(KEY_AMMO))
                	nbtcompound.setInteger(KEY_AMMO, nbtcompound.getInteger(KEY_AMMO)+1);
                else nbtcompound.setInteger(KEY_AMMO, 1);
                
                itemStackIn.setTagCompound(nbtcompound);
                break;
            }
        }
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!stack.hasTagCompound()) {
			NBTTagCompound nbtcompound = new NBTTagCompound();
			nbtcompound.setInteger(KEY_AMMO, 0);
			stack.setTagCompound(nbtcompound);
		}
		else if (!stack.getTagCompound().hasKey(KEY_AMMO)) {
			NBTTagCompound nbtcompound = new NBTTagCompound();
			nbtcompound.setInteger(KEY_AMMO, 0);
			stack.setTagCompound(nbtcompound);
		}
		;
		if (cd > 0)
			cd--;
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		NBTTagCompound nbtcompound = new NBTTagCompound();
		nbtcompound.setInteger(KEY_AMMO, 0);
		stack.setTagCompound(nbtcompound);
		super.onCreated(stack, worldIn, playerIn);
	}
	
}
