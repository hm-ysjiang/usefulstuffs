package hmysjiang.usefulstuffs.items;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.entity.EntityLightBulb;
import hmysjiang.usefulstuffs.init.ModBlocks;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemLightShooter extends Item {
	
	private static final int FULL_COOLDOWN = 30;
	private static final int MAX_AMMO = 256;
	
	public ItemLightShooter() {
		this(Reference.ModItems.LIGHT_SHOOTER.getUnlocalizedName(), Reference.ModItems.LIGHT_SHOOTER.getRegistryName());
	}
	
	public ItemLightShooter(String unlocalized, String registry) {
		setUnlocalizedName(unlocalized);
		setRegistryName(registry);
		this.maxStackSize = 1;
		setMaxDamage(FULL_COOLDOWN + 1);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag advanced) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Ammo")) {
			tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.light_shooter.tooltip_1", String.valueOf(stack.getTagCompound().getInteger("Ammo")), String.valueOf(this.MAX_AMMO)));
		}
		else {
			tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.light_shooter.tooltip_1", 0, String.valueOf(this.MAX_AMMO)));
		}
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.light_shooter.tooltip_2"));
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.light_shooter.tooltip_3"));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
		ItemStack shooter = playerIn.getHeldItem(hand);
		//charge
		if (playerIn.isSneaking()) {
			if (!worldIn.isRemote)
				this.addAmmo(playerIn, shooter);
		}
		//launch
		else if (shooter.hasTagCompound() && shooter.getTagCompound().hasKey("Ammo") && shooter.getItemDamage() == 0) {
			worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.PLAYERS, 1.0F, 1.0F);
			if (!worldIn.isRemote)
				this.launchAmmo(shooter, worldIn, playerIn);
		}
		return super.onItemRightClick(worldIn, playerIn, hand);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (stack.isItemDamaged()) {
			stack.setItemDamage(stack.getItemDamage() - 1);
			if (!stack.isItemDamaged() && entityIn instanceof EntityPlayer)
				worldIn.playSound((EntityPlayer) entityIn, entityIn.getPosition(), SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.PLAYERS, 1.0F, 1.0F);
		}
		if (!stack.hasTagCompound()) {
			NBTTagCompound nbtcompound = new NBTTagCompound();
			nbtcompound.setInteger("Ammo", 0);
			stack.setTagCompound(nbtcompound);
		}
		else if (!stack.getTagCompound().hasKey("Ammo")) {
			NBTTagCompound nbtcompound = new NBTTagCompound();
			nbtcompound.setInteger("Ammo", 0);
			stack.setTagCompound(nbtcompound);
		}
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		NBTTagCompound nbtcompound = new NBTTagCompound();
		nbtcompound.setInteger("Ammo", 0);
		stack.setTagCompound(nbtcompound);
		super.onCreated(stack, worldIn, playerIn);
	}
	
	protected void launchAmmo(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		NBTTagCompound nbtcompound = itemStackIn.getTagCompound();
		if (nbtcompound.getInteger("Ammo") > 0) {
			EntityLightBulb entity = new EntityLightBulb(worldIn, playerIn);
			entity.setThrowableHeading(playerIn.getLookVec().x, playerIn.getLookVec().y, playerIn.getLookVec().z, 1.5F, 0);
			worldIn.spawnEntity(entity);
			
			nbtcompound.setInteger("Ammo", nbtcompound.getInteger("Ammo")-1);
			itemStackIn.setTagCompound(nbtcompound);
			itemStackIn.setItemDamage(FULL_COOLDOWN);
		}
	}

	protected void addAmmo(EntityPlayer playerIn, ItemStack itemStackIn) {
		for (ItemStack itemstack : playerIn.inventory.mainInventory) {
			if (itemStackIn.getTagCompound().getInteger("Ammo") == MAX_AMMO)
				break; 
			if (!itemstack.isEmpty() && itemstack.isItemEqual(new ItemStack(ModBlocks.light_bulb))) {
				itemstack.setCount(itemstack.getCount() - 1);
				if (itemstack.isEmpty())
					playerIn.inventory.deleteStack(itemstack);
				NBTTagCompound nbtcompound;
				if (itemStackIn.hasTagCompound()) 
					nbtcompound = itemStackIn.getTagCompound();
				else nbtcompound = new NBTTagCompound();
				
				if (nbtcompound.hasKey("Ammo"))
					nbtcompound.setInteger("Ammo", nbtcompound.getInteger("Ammo")+1);
				else nbtcompound.setInteger("Ammo", 1);
				
				itemStackIn.setTagCompound(nbtcompound);
				break;
			}
		}
	}
	
	public static int getMaxAmmo() {
		return MAX_AMMO;
	}
	
}