package hmysjiang.usefulstuffs.blocks.fierylily;

import hmysjiang.usefulstuffs.init.ModBlocks;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemFieryLilyPad extends ItemBlock {

	public ItemFieryLilyPad(BlockFieryLilyPad block) {
		super(block);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		return EnumActionResult.FAIL;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);

		if (raytraceresult == null) {
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
		}
		else {
			if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
				BlockPos lava = raytraceresult.getBlockPos();

				if (!worldIn.isBlockModifiable(playerIn, lava) || !playerIn.canPlayerEdit(lava.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack)) {
					return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
				}

				BlockPos lily = lava.up();
				IBlockState lavastate = worldIn.getBlockState(lava);

				if (lavastate.getMaterial() == Material.LAVA && ((Integer)lavastate.getValue(BlockLiquid.LEVEL)).intValue() == 0 && worldIn.isAirBlock(lily)) {
					worldIn.setBlockState(lily, ModBlocks.fiery_lily.getDefaultState(), 11);

					if (!playerIn.capabilities.isCreativeMode) {
						itemstack.shrink(1);
					}

					playerIn.addStat(StatList.getObjectUseStats(this));
					worldIn.playSound(playerIn, lava, SoundEvents.BLOCK_WATERLILY_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
				}
			}
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
		}
	}

}
