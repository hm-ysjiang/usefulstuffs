package hmysjiang.usefulstuffs.items;

import java.util.List;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.blocks.lightbulb.BlockLightBulb;
import hmysjiang.usefulstuffs.client.gui.GuiHandler;
import hmysjiang.usefulstuffs.entity.EntityLightBulb;
import hmysjiang.usefulstuffs.init.ModBlocks;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class ItemLightShooter extends Item {
	
	public static int cooldown;
	public static final int MAX_AMMO = 256;
	
	public ItemLightShooter() {
		this(Reference.ModItems.LIGHT_SHOOTER.getUnlocalizedName(), Reference.ModItems.LIGHT_SHOOTER.getRegistryName());
	}
	
	public ItemLightShooter(String unlocalized, String registry) {
		setUnlocalizedName(unlocalized);
		setRegistryName(registry);
		this.maxStackSize = 1;
		cooldown = ConfigManager.shooterCD;
		if (cooldown > 0)
			setMaxDamage(cooldown);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack shooter = playerIn.getHeldItem(handIn);
		//charge
		if (playerIn.isSneaking()) {
			if (!worldIn.isRemote) 
				playerIn.openGui(UsefulStuffs.instance, GuiHandler.GUI_LIGHT_SHOOTER, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
		}
		//launch
		else if (shooter.getItemDamage() == 0) {
			worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.PLAYERS, 1.0F, 1.0F);
			if (!worldIn.isRemote)
				this.launch(shooter, worldIn, playerIn);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	protected void launch(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		if (getAmmo(itemStackIn) > 0) {
			EntityLightBulb entity = new EntityLightBulb(worldIn, playerIn);
			entity.setThrowableHeading(playerIn.getLookVec().x, playerIn.getLookVec().y, playerIn.getLookVec().z, 2.0F, 0);
			worldIn.spawnEntity(entity);
			itemStackIn.setItemDamage(cooldown);
			decrAmmoCount(itemStackIn, 1);
		}
	}

	public static int getAmmo(ItemStack itemStackIn) {
		int ammo = 0;
		ItemStackHandler handler = getItemHandler(itemStackIn, "Cont");
		for (int i = 0 ; i<handler.getSlots() ; i++) {
			ItemStack stack = handler.getStackInSlot(i);
			if (!stack.isEmpty() && stack.isItemEqual(new ItemStack(ModBlocks.light_bulb)))
				ammo +=stack.getCount();
		}
		return ammo;
	}
	
	protected static ItemStackHandler getItemHandler(ItemStack itemStackIn, String key) {
		ItemStackHandler handler = new ItemStackHandler(4);
		if (itemStackIn.hasTagCompound() && itemStackIn.getTagCompound().hasKey("Cont"))
			handler.deserializeNBT(itemStackIn.getTagCompound().getCompoundTag("Cont"));
		else {
			NBTTagCompound compound = new NBTTagCompound();
			compound.setTag("Cont", handler.serializeNBT());
			itemStackIn.setTagCompound(compound);
		}
		return handler;
	}

	public static void decrAmmoCount(ItemStack itemStackIn, int amount) {
		ItemStackHandler handler = getItemHandler(itemStackIn, "Cont");
		for (int i = 0 ; i<handler.getSlots() ; i++)
			if (!handler.extractItem(i, 1, false).isEmpty())
				break;
		itemStackIn.getTagCompound().setTag("Cont", handler.serializeNBT());
	}

	//return the remainder
	public static int incrAmmoCount(ItemStack itemStackIn, int amount) {
		ItemStackHandler handler = getItemHandler(itemStackIn, "Cont");
		for (int i = 0 ; i<handler.getSlots() ; i++) {
			if (amount <= 0)
				break;
			int per = amount > 64 ? 64 : amount;
			amount -= per;
			ItemStack ret = handler.insertItem(i, new ItemStack(ModBlocks.light_bulb, per), false);
			amount += ret.getCount();
		}
		itemStackIn.getTagCompound().setTag("Cont", handler.serializeNBT());
		return amount;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag advanced) {
		if (getAmmo(stack) > 0) {
			tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.light_shooter.tooltip_1", String.valueOf(getAmmo(stack)), String.valueOf(this.MAX_AMMO)));
		}
		else {
			tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.light_shooter.tooltip_1", 0, String.valueOf(this.MAX_AMMO)));
		}
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.light_shooter.tooltip_2"));
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.light_shooter.tooltip_3"));
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (stack.isItemDamaged()) {
			stack.setItemDamage(stack.getItemDamage() - 1);
			if (!stack.isItemDamaged() && entityIn instanceof EntityPlayer)
				worldIn.playSound((EntityPlayer) entityIn, entityIn.getPosition(), SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.PLAYERS, 1.0F, 1.0F);
		}
	}
	
}