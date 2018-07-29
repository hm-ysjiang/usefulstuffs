package hmysjiang.usefulstuffs.entity;

import javax.annotation.Nullable;

import hmysjiang.usefulstuffs.network.PacketHandler;
import hmysjiang.usefulstuffs.network.packet.FLDead;
import hmysjiang.usefulstuffs.utils.helper.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

//Unused
public class EntityFairyLight extends Entity implements IGlowable {
	
	public static final String KEY_POS = "Pos";
	private static final DataParameter<Boolean> DEAD_SERVER = EntityDataManager.<Boolean>createKey(EntityFairyLight.class, DataSerializers.BOOLEAN);
	
	private boolean prevMotive;
	private boolean packetSend;
	private BlockPos parentPos;
	
	public EntityFairyLight(World worldIn, BlockPos pos, @Nullable BlockPos parentPos) {
		this(worldIn, pos);
		this.parentPos = parentPos;
	}
	public EntityFairyLight(World worldIn, BlockPos pos) {
		this(worldIn);
		this.setPosition(pos.getX()+0.5, pos.getY()+1.5, pos.getZ()+0.5);
	}
	public EntityFairyLight(World worldIn) {
		super(worldIn);
		
		this.prevMotive = false;
		packetSend = false;
	}
	 
	@Override
	protected void entityInit() {
		dataManager.register(DEAD_SERVER, Boolean.valueOf(false));
	}
	
	//No inaccuracy, spawn at the middle of the block.Perfect.
	@Override
	public void setPosition(double x, double y, double z) {
		posX = x;
		posY = y;
		posZ = z;
		setEntityBoundingBox(new AxisAlignedBB(x-0.15, y-0.15, z-0.15, x+0.15, y+0.15, z+0.15));
	}
	
	//The child(FairyLights) handle light updates and motion calcs.Boudary checks are parents' work.
	@Override
	public void onUpdate() {
		setLight();
		
		//This runs on the client side to check if self dead on server side
		if (world.isRemote) {
			if (dataManager.get(DEAD_SERVER) && !packetSend) {
				packetSend = true;
				PacketHandler.INSTANCE.sendToServer(new FLDead(new BlockPos(this)));
			}
		}
		
		 
		if (!prevMotive) {
			if (!world.isRemote) {
				applyMotion();
			}
			prevMotive = true;
		} 
		else {
			prevMotive = false;
		}
		
		super.onUpdate();
	}
	
	@Override
	public void onKillCommand() {
		markDeadOnServer();
//		notifyParent();
	}
	
	/***
	 * This does not remove the light, use onKillCommand() instead.
	 */
	@Override @Deprecated
	public void setDead() {
		super.setDead();
	}
	
	public void markDeadOnServer(){
		dataManager.set(DEAD_SERVER, Boolean.valueOf(true));
	}
	
	protected void notifyParent() {
//		((TileEntityLantern)worldObj.getTileEntity(parentPos)).removeFLFromChildren();
	}

	@Override
	public void setLight() {
		this.world.setLightFor(EnumSkyBlock.BLOCK, new BlockPos(this), (14*(dataManager.get(DEAD_SERVER) ? 0 : 1)));
		WorldHelper.updateLightInArea(world, posX, posY, posZ);
	}

	protected void applyMotion() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		if (compound.hasKey(KEY_POS)) {
			int[] arr = compound.getIntArray(KEY_POS);
			this.parentPos = new BlockPos(arr[0], arr[1], arr[2]);
		}
		else {
			this.parentPos = new BlockPos(0, 0, 0);
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		if (parentPos != null)
			compound.setIntArray(KEY_POS, new int[] {parentPos.getX(), parentPos.getY(), parentPos.getZ()});
	}

}
