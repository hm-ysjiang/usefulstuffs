package hmysjiang.usefulstuffs.tileentity;

import java.util.ArrayList;
import java.util.List;

import hmysjiang.usefulstuffs.miscs.helper.WorldHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//unused
public class TileEntityLantern extends TileEntity implements ITickable {
	
//	public static final String KEY_CHILDREN = "Children";
	public static final String KEY_FRIEND = "Friends";
	
//	private List<EntityFairyLight> children;
	private List<BlockPos> friends;
	private int internalTick;
	
	public TileEntityLantern() {
		internalTick = 0;
	}

	@Override
	public void update() {
//		if (!worldObj.isRemote) {
			internalTick++;
			internalTick%=100; //Update per 5 sec
			
			if (internalTick == 0) {
				for (int x = -2 ; x<=2 ; x++) {
					for (int y = -2 ; y<=2 ; y++) {
						for (int z = -2 ; z<=2 ; z++) {
							if (x == 0 && y == 0 && z == 0)
								continue;
							BlockPos bpos = new BlockPos(pos.getX()+x, pos.getY()+y, pos.getZ()+z);
							worldObj.setLightFor(EnumSkyBlock.BLOCK, bpos, 15);
							WorldHelper.updateLightInArea(worldObj, pos.getX(), pos.getY(), pos.getZ());	
						}
					}
				}
			}
			
//		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		friends = new ArrayList<BlockPos>();
		if (compound.hasKey(KEY_FRIEND)) {
			NBTTagList list = compound.getTagList(KEY_FRIEND, Constants.NBT.TAG_INT_ARRAY);
			for (int i = 0 ; i<list.tagCount() ; i++) {
				int[] pos = list.getIntArrayAt(i);
				friends.add(new BlockPos(pos[0], pos[1], pos[2]));
			}
		}	
		
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList list = new NBTTagList();
		for (BlockPos pos:friends) {
			list.appendTag(new NBTTagIntArray(new int[] {pos.getX(), pos.getY(), pos.getZ()}));
		}
		compound.setTag(KEY_FRIEND, list);		
		
		return super.writeToNBT(compound);
	}

}
