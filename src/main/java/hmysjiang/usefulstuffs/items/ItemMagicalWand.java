package hmysjiang.usefulstuffs.items;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.entity.EntityFairyLight;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMagicalWand extends Item {
	
	/***
	 * Item for testing
	 */
	public ItemMagicalWand() {
		setUnlocalizedName(Reference.ModItems.MAGICAL_WAND.getUnlocalizedName());
		setRegistryName(Reference.ModItems.MAGICAL_WAND.getRegistryName());
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			worldIn.spawnEntityInWorld(new EntityFairyLight(worldIn, new BlockPos(playerIn.posX, playerIn.posY, playerIn.posZ), null));
		}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		if (!entityLiving.worldObj.isRemote) {
			if (entityLiving.isSneaking()) {
				for (Entity entity:entityLiving.worldObj.getEntitiesWithinAABBExcludingEntity(entityLiving, new AxisAlignedBB(new BlockPos(entityLiving.posX-1, entityLiving.posY, entityLiving.posZ-1), new BlockPos(entityLiving.posX+2, entityLiving.posY+3, entityLiving.posZ+2)))) {
					entity.onKillCommand();
				}
			}
			else {
				for (Entity entity:entityLiving.worldObj.getEntitiesWithinAABBExcludingEntity(entityLiving, new AxisAlignedBB(new BlockPos(entityLiving.posX-1, entityLiving.posY, entityLiving.posZ-1), new BlockPos(entityLiving.posX+2, entityLiving.posY+3, entityLiving.posZ+2)))) {
					entity.setInvisible(true);
				}
			}
		}
		return super.onEntitySwing(entityLiving, stack);
	}
	
}