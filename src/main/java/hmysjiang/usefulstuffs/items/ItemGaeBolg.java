package hmysjiang.usefulstuffs.items;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.entity.projectiles.EntityGaeBolg;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemGaeBolg extends Item {
	
	public static final int MAX_CD = 20*150;
	
	public ItemGaeBolg() {
		setUnlocalizedName(Reference.ModItems.GAEBOLG.getUnlocalizedName());
		setRegistryName(Reference.ModItems.GAEBOLG.getRegistryName());
		setMaxStackSize(1);
		setNoRepair();
		setMaxDamage(MAX_CD);
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		if (!entityLiving.worldObj.isRemote && entityLiving instanceof EntityPlayer && stack.getItemDamage() == 0) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			EntityGaeBolg entity = new EntityGaeBolg(player.worldObj, player);
			entity.setAim(player, player.rotationPitch, player.rotationYaw, 0, 10.0F, 0);
//			entity.setInvisible(true);
			player.worldObj.spawnEntityInWorld(entity);
			stack.setItemDamage(MAX_CD-1);
		}
		return false;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		EntityPlayer player = (EntityPlayer) entityIn;
		player.addPotionEffect(new PotionEffect(Potion.getPotionById(27), 8, 999, false, false));
		if (stack.isItemDamaged())
			stack.setItemDamage(stack.getItemDamage()-1);
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}
	
}