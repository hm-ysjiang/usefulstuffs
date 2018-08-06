package hmysjiang.usefulstuffs.blocks.campfire;

import java.util.List;

import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.utils.WorldHelper;
import net.minecraft.block.BlockLog;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class TileEntityCampfire extends TileEntity implements ITickable {

	private int buffRadius;
	
	public TileEntityCampfire() {
		buffRadius = 3;
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			if (WorldHelper.hasNoBlockBelow(world, pos)) {
				world.setBlockState(pos, Blocks.AIR.getDefaultState());
				world.spawnEntity(new EntityItem(world, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, new ItemStack(ModBlocks.campfire)));
				return;
			}
			buffRadius = 3 + MathHelper.ceil(getModifierCounts()/2);
			List<Entity> entitylist = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos.getX()-buffRadius, pos.getY()-5, pos.getZ()-buffRadius, pos.getX()+buffRadius+1, pos.getY()+5, pos.getZ()+buffRadius+1));
			for (Entity entity:entitylist)
				if (entity instanceof EntityLivingBase)
					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.getPotionById(10), 8, 1, false, false));
		}
	}

	private double getModifierCounts() {
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
	
}
