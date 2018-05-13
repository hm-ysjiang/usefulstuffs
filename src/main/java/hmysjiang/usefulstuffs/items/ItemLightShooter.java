package hmysjiang.usefulstuffs.items;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.entity.projectiles.EntityLightBulb;
import hmysjiang.usefulstuffs.init.ModBlocks;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import scala.collection.generic.GenericClassTagCompanion;

public class ItemLightShooter extends Item {
	
	private static final int FULL_COOLDOWN = 30;
	private static final int MAX_AMMO = 256;
	private static final String KEY_AMMO = "Ammo";
	
	protected int ammoStored;
	protected int cd;
	
	public ItemLightShooter() {
		this(Reference.ModItems.LIGHT_SHOOTER.getUnlocalizedName(), Reference.ModItems.LIGHT_SHOOTER.getRegistryName());
	}
	
	public ItemLightShooter(String unlocalized, String registry) {
		setUnlocalizedName(unlocalized);
		setRegistryName(registry);
		this.maxStackSize = 1;
		
		cd = 0;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(KEY_AMMO)) {
			tooltip.add(I18n.format("usefulstuffs.light_shooter.tooltip_1", String.valueOf(stack.getTagCompound().getInteger(KEY_AMMO)), String.valueOf(this.MAX_AMMO)));
			tooltip.add(I18n.format("usefulstuffs.light_shooter.tooltip_2"));
			tooltip.add(I18n.format("usefulstuffs.light_shooter.tooltip_3"));
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
//		if (!worldIn.isRemote) {
			//charge
			if (playerIn.isSneaking()) {
				this.addAmmo(playerIn, itemStackIn);
			}
			//launch
			else if (cd == 0 && itemStackIn.hasTagCompound() && itemStackIn.getTagCompound().hasKey(KEY_AMMO)) {
				this.launchAmmo(itemStackIn, worldIn, playerIn);
			}
//		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
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
		if (cd > 0) {
			cd--;
			if (cd == 0)
				worldIn.playSound((EntityPlayer) entityIn, entityIn.getPosition(), SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.PLAYERS, 3.0F, 1.0F);
		}
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		NBTTagCompound nbtcompound = new NBTTagCompound();
		nbtcompound.setInteger(KEY_AMMO, 0);
		stack.setTagCompound(nbtcompound);
		super.onCreated(stack, worldIn, playerIn);
	}
	
	protected void launchAmmo(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.PLAYERS, 3.0F, 1.0F);
		if (!worldIn.isRemote) {
			NBTTagCompound nbtcompound = itemStackIn.getTagCompound();
			if (nbtcompound.getInteger(KEY_AMMO) > 0) {
				EntityLightBulb entity = new EntityLightBulb(worldIn, playerIn);
				entity.setThrowableHeading(playerIn.getLookVec().xCoord, playerIn.getLookVec().yCoord, playerIn.getLookVec().zCoord, 1.5F, 0);
//				entity.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
				worldIn.spawnEntityInWorld(entity);
				
				nbtcompound.setInteger(KEY_AMMO, nbtcompound.getInteger(KEY_AMMO)-1);
				itemStackIn.setTagCompound(nbtcompound);

				this.cd = FULL_COOLDOWN;
			}
		}
	}

	protected void addAmmo(EntityPlayer playerIn, ItemStack itemStackIn) {
		if (!playerIn.worldObj.isRemote) {
			for (ItemStack itemstack : playerIn.inventory.mainInventory) {
				if (itemStackIn.getTagCompound().getInteger(KEY_AMMO) == MAX_AMMO)
					break; 
				if (itemstack != null && itemstack.isItemEqual(new ItemStack(ModBlocks.lightbulb))) {
					if (--itemstack.stackSize == 0)
						playerIn.inventory.deleteStack(itemstack);
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
		
	}
	
	public static int getMaxAmmo() {
		return MAX_AMMO;
	}
	
}
