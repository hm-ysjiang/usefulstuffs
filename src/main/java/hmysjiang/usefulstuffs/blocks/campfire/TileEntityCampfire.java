package hmysjiang.usefulstuffs.blocks.campfire;

import java.util.List;

import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.utils.helper.WorldHelper;
import net.minecraft.block.BlockLog;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCampfire extends TileEntity implements ITickable, ICapabilityProvider {

	private int buffRadius;
	private ItemStackHandler handler;
	private int timeLeft;
	private int fuelTime;
	
	public TileEntityCampfire() {
		buffRadius = 3;
		handler = new ItemStackHandler(1);
		timeLeft = 0;
		fuelTime = 0;
	}

	@Override
	public void update() {
		if (WorldHelper.hasNoBlockBelow(world, pos)) {
			world.setBlockToAir(pos);
			return;
		}
		if (isBurning() && BlockCampfire.needFuel) {
			timeLeft = timeLeft > 0 ? timeLeft - (3 + MathHelper.ceil(getModifierCounts()/2)) / 3 : 0;
			if (timeLeft <= 0) {
				timeLeft = getBurnTime();
				fuelTime = timeLeft;
				handler.extractItem(0, 1, false);
			}
		}
		if (timeLeft > 0 || !BlockCampfire.needFuel) {
			buffRadius = 3 + MathHelper.ceil(getModifierCounts()/2);
			List<Entity> entitylist = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos.getX() + 0.5 - (double)buffRadius / 2, pos.getY() - 3, pos.getZ() + 0.5 - (double)buffRadius / 2, 
																										 pos.getX() + 0.5 + (double)buffRadius / 2, pos.getY() + 3, pos.getZ() + 0.5 + (double)buffRadius / 2));
			for (Entity entity:entitylist)
				if (entity instanceof EntityLivingBase)
					if (((EntityLivingBase)entity).getActivePotionEffect(Potion.getPotionById(10)) == null || ((EntityLivingBase)entity).getActivePotionEffect(Potion.getPotionById(10)).getDuration() < 30)
						((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.getPotionById(10), 100, 1, false, false));	
		}
	}

	public double getModifierCounts() {
		int count = 0;
		for (int x = -2 ; x<=2 ; x++) 
			for (int z = -2 ; z<=2 ; z++) 
				if (world.getBlockState(new BlockPos(pos.getX()+x, pos.getY()-1, pos.getZ()+z)).getBlock() instanceof BlockLog) 
					count++;
		return count;
	}
	
	public int getBuffRadius() {
		return buffRadius;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		handler.deserializeNBT(compound.getCompoundTag("Cont"));
		timeLeft = compound.getInteger("timeLeft");
		fuelTime = compound.getInteger("fuel");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("Cont", handler.serializeNBT());
		compound.setInteger("timeLeft", timeLeft);
		compound.setInteger("fuel", fuelTime);
		return super.writeToNBT(compound);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return this.getCapability(capability, facing) != null;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (BlockCampfire.needFuel) {
			if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing == EnumFacing.DOWN)
				return (T) handler;
		}
		else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing == null) {
			return (T) handler;
		}
		return null;
	}
	
	public boolean isBurning() {
		return timeLeft > 0 || !handler.getStackInSlot(0).isEmpty();
	}
	
	public int getTimeLeft() {
		return timeLeft < 0 ? 0 : timeLeft;
	}
	
	public int getFuelTime() {
		return fuelTime;
	}
	
	public int getBurnTime() {
		ItemStack fuel = handler.getStackInSlot(0);
		if (fuel.getItem().getItemBurnTime(fuel) > 0) return fuel.getItem().getItemBurnTime(fuel);
		return TileEntityFurnace.getItemBurnTime(fuel);
	}
	
}
