package hmysjiang.usefulstuffs.items.gun;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.entity.projectiles.EntityBullet;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.items.variants.EnumBulletVariants;
import hmysjiang.usefulstuffs.tileentity.capability.FilteredItemStackHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class ItemHandGun extends ItemGunBase {
	
	public ItemHandGun() {
		setUnlocalizedName(Reference.ModItems.HAND_GUN.getUnlocalizedName());
		setRegistryName(Reference.ModItems.HAND_GUN.getRegistryName());
	}

	@Override
	protected void launchAmmo(EntityPlayer player, ItemStack stack) {
		ItemStackHandler handler = new ItemStackHandler(4);
		handler.deserializeNBT(stack.getTagCompound().getCompoundTag("Cont"));
		ItemStack bullet = handler.getStackInSlot(0);
		bullet.stackSize--;
		if(bullet.stackSize == 0)
			handler.setStackInSlot(0, null);
		for (int i = 1 ; i<4 ; i++) {
			if (handler.insertItem(i, new ItemStack(ModItems.bullet, 1, 0), true) == null) {
				handler.insertItem(i, new ItemStack(ModItems.bullet, 1, 0), false);
				break;
			}
		}
		stack.getTagCompound().setTag("Cont", handler.serializeNBT());
		
		if (!player.worldObj.isRemote) {
			EntityBullet entity = new EntityBullet(player.worldObj, player);
			entity.setAim(player, player.rotationPitch, player.rotationYaw, 0.0F, 4.0F, 4.0F);
			entity.setDamage(2);
			entity.setType(EnumBulletVariants.NORMAL.getID());
			player.worldObj.spawnEntityInWorld(entity);
		}
	}
	
}
