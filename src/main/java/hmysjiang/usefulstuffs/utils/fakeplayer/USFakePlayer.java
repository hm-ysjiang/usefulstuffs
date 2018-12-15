package hmysjiang.usefulstuffs.utils.fakeplayer;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

public class USFakePlayer extends FakePlayer {

	public USFakePlayer(WorldServer world, GameProfile name) {
		super(world, name);
		this.capabilities.disableDamage = true;
	}

	public USFakePlayer(WorldServer world, GameProfile name, double x, double y, double z, EnumFacing facing) {
		this(world, name);
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		if (facing == EnumFacing.DOWN || facing == EnumFacing.UP) 
			this.rotationPitch = facing == EnumFacing.UP ? -90 : 90;
		else {
			this.rotationPitch = 0;
			this.rotationYaw = facing.getHorizontalAngle();
		}
		Vec3d adj = new Vec3d(facing.getDirectionVec()).scale(0.6);
		this.posX += adj.x;
		this.posY += adj.y;
		this.posZ += adj.z;
	}
	
	public void setReadyToAttack() {
		this.ticksSinceLastSwing = (int) this.getCooldownPeriod() + 1;
		this.onGround = true;
	}
	
	@Override
	public void attackTargetEntityWithCurrentItem(Entity targetEntity) {
		this.setReadyToAttack();
		super.attackTargetEntityWithCurrentItem(targetEntity);
	}
	
	@Override
	public boolean addItemStackToInventory(ItemStack p_191521_1_) {
		for (int i = 0 ; i<9 ; i++) 
			if (this.inventory.getStackInSlot(i).isEmpty()) 
				return super.addItemStackToInventory(p_191521_1_);
		if (!world.isRemote)
			world.spawnEntity(new EntityItem(world, posX, posY + 1.5, posZ, p_191521_1_));
		return true;
	}
	
	@Override
	public void displayGui(IInteractionObject guiOwner) {}
	@Override
	public void displayGUIChest(IInventory chestInventory) {}
	@Override
	public void displayGuiCommandBlock(TileEntityCommandBlock commandBlock) {}
	@Override
	public void displayGuiEditCommandCart(CommandBlockBaseLogic commandBlock) {}
	@Override
	public void displayVillagerTradeGui(IMerchant villager) {}
	@Override
	public void sendEnterCombat() {}
	@Override
	public void sendEndCombat() {}
	@Override
	public SleepResult trySleep(BlockPos bedLocation) { return SleepResult.OTHER_PROBLEM; }
	@Override
	public boolean startRiding(Entity entityIn) { return false; }
	@Override
	public boolean startRiding(Entity entityIn, boolean force) { return false; }
	@Override
	public void openEditSign(TileEntitySign signTile) {}
	@Override
	public void openGuiHorseInventory(AbstractHorse horse, IInventory inventoryIn) {}
	@Override
	protected void onNewPotionEffect(PotionEffect id) {}
	@Override
	protected void onChangedPotionEffect(PotionEffect id, boolean p_70695_2_) {}
	@Override
	protected void onFinishedPotionEffect(PotionEffect effect) {}
	@Override
	public void setPositionAndUpdate(double x, double y, double z) {}
	@Override
	public void sendPlayerAbilities() {}
	@Override
	public void setGameType(GameType gameType) {}
	@Override
	public String getPlayerIP() { return ""; }
	@Override
	public boolean hitByEntity(Entity entityIn) { return true; }
	@Override
	public boolean canAttackPlayer(EntityPlayer player) { return true; }
	@Override
	public boolean canBeAttackedWithItem() { return false; }
	@Override
	public boolean canBeCollidedWith() { return false; }

}
