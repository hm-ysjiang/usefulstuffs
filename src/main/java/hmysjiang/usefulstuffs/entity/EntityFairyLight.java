package hmysjiang.usefulstuffs.entity;

import hmysjiang.usefulstuffs.miscs.helper.WorldHelper;
import hmysjiang.usefulstuffs.network.FLDead;
import hmysjiang.usefulstuffs.network.PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFairyLight extends Entity implements IGlowable {
	
//	private static final DataParameter<Boolean> DEAD_CLIENT = EntityDataManager.<Boolean>createKey(EntityFairyLight.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> DEAD_SERVER = EntityDataManager.<Boolean>createKey(EntityFairyLight.class, DataSerializers.BOOLEAN);
	
	private boolean prevMotive;
	private boolean packetSend;
	
	public EntityFairyLight(World worldIn, BlockPos playerPos) {
		this(worldIn);
		this.setPosition(playerPos.getX()+0.5, playerPos.getY()+1.5, playerPos.getZ()+0.5);
	}
	public EntityFairyLight(World worldIn) {
		super(worldIn);
		
		this.prevMotive = false;
		packetSend = false;
	}

	
	@Override
	public void setLight() {
		this.worldObj.setLightFor(EnumSkyBlock.BLOCK, new BlockPos(this), (15*(dataManager.get(DEAD_SERVER) ? 0 : 1)));
		WorldHelper.updateLightInArea(worldObj, posX, posY, posZ);
	}
	
	@Override
	public void onUpdate() {
		setLight();
		 
		if (!prevMotive) {
			if (!worldObj.isRemote) {
				applyMotion();
			}
			prevMotive = true;
		} 
		else {
			prevMotive = false;
		}	

		if (!isInvisible())
			setInvisible(true);
		
		
		//Client
		if (worldObj.isRemote) {
			if (dataManager.get(DEAD_SERVER) && !packetSend) {
				System.out.println("Set client to dead");
				packetSend = true;
				PacketHandler.INSTANCE.sendToServer(new FLDead(new BlockPos(this)));
			}
		}
		
		
		super.onUpdate();
	}
	
	@Override
	public void onKillCommand() {
		markDeadOnServer();
	}
	
	public void markDeadOnServer(){
		dataManager.set(DEAD_SERVER, Boolean.valueOf(true));
	}
	
	@Override
	public void setPosition(double x, double y, double z) {
		posX = x;
		posY = y;
		posZ = z;
		setEntityBoundingBox(new AxisAlignedBB(x-0.15, y-0.15, z-0.15, x+0.15, y+0.15, z+0.15));
//		super.setPosition(x, y, z);
	}

	private void applyMotion() {
		
	}
 
	@Override
	protected void entityInit() {
//		dataManager.register(DEAD_CLIENT, Boolean.valueOf(false));
		dataManager.register(DEAD_SERVER, Boolean.valueOf(false));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
	}

}
