package hmysjiang.usefulstuffs.items;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemInfiniteWater extends Item {
	
	public ItemInfiniteWater() {
		setUnlocalizedName(Reference.ModItems.INIFNITE_WATER.getUnlocalizedName());
		setRegistryName(Reference.ModItems.INIFNITE_WATER.getRegistryName());
		setMaxStackSize(1);
		setMaxDamage(2);
		setNoRepair();
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
//		if (!worldIn.isRemote) {
			ItemStack bucket = playerIn.getHeldItem(handIn);
			int fillLevel = 2 - bucket.getItemDamage();
			boolean place = playerIn.isSneaking();
			RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, !place);
			if (raytraceresult == null)	
				return new ActionResult<ItemStack>(EnumActionResult.PASS, bucket);
			if (raytraceresult.typeOfHit != Type.BLOCK) 
				return new ActionResult<ItemStack>(EnumActionResult.PASS, bucket);
			BlockPos pos = raytraceresult.getBlockPos();
			if (worldIn.isBlockModifiable(playerIn, pos)) {
				if (place) {
					if (fillLevel ==  0)
						return new ActionResult<ItemStack>(EnumActionResult.FAIL, bucket);
					
					//Try to access the fluid handler
					RayTraceResult ray2 = this.rayTrace(worldIn, playerIn, true);
					if (worldIn.getTileEntity(ray2.getBlockPos()) != null && worldIn.getTileEntity(ray2.getBlockPos()).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, ray2.sideHit)) {
						IFluidHandler handler = worldIn.getTileEntity(ray2.getBlockPos()).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, ray2.sideHit);
						if (handler.fill(new FluidStack(FluidRegistry.WATER, 1000), false) > 0) {
							handler.fill(new FluidStack(FluidRegistry.WATER, 1000), true);
							if (worldIn.isRemote)
								worldIn.playSound(playerIn, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
							return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, bucket);
						}
					}
					
					boolean replace = worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
					pos = replace && raytraceresult.sideHit == EnumFacing.UP ? pos : pos.offset(raytraceresult.sideHit);
					if (!playerIn.canPlayerEdit(pos, raytraceresult.sideHit, bucket)) 
						return new ActionResult<ItemStack>(EnumActionResult.FAIL, bucket);
					else if (this.placeWater(worldIn, pos, playerIn)) {
						if (fillLevel == 1)
							bucket.setItemDamage(2);
						return new ActionResult(EnumActionResult.SUCCESS, bucket);
					}
					return new ActionResult<ItemStack>(EnumActionResult.FAIL, bucket);
				}
				else {
					if (!playerIn.canPlayerEdit(pos.offset(raytraceresult.sideHit), raytraceresult.sideHit, bucket)) 
						return new ActionResult<ItemStack>(EnumActionResult.FAIL, bucket);
					IBlockState state = worldIn.getBlockState(pos);
					if (state.getMaterial() == Material.WATER && ((Integer)state.getValue(BlockLiquid.LEVEL)).intValue() == 0) {
						worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
						playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
						return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, this.fillWater(bucket, playerIn));
					}
					return new ActionResult<ItemStack>(EnumActionResult.FAIL, bucket);
				}
			}
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, bucket);
//		}
//		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}

	private ItemStack fillWater(ItemStack bucket, EntityPlayer playerIn) {
		if (bucket.getItemDamage() > 0)
			bucket.setItemDamage(bucket.getItemDamage() - 1);
		return bucket;
	}

	private boolean placeWater(World world, BlockPos pos, EntityPlayer player) {
		IBlockState state = world.getBlockState(pos);
		boolean notSolid = !state.getMaterial().isSolid();

		if (!world.isAirBlock(pos) && !notSolid) {
			return false;
		}
		else {
			if (world.provider.doesWaterVaporize()) {
				int x = pos.getX();
				int y = pos.getY();
				int z = pos.getZ();
				world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

				for (int i = 0; i < 8; ++i) {
					world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double) x + Math.random(), (double) i + Math.random(), (double) z + Math.random(), 0.0D, 0.0D, 0.0D);
				}
			}
			else {
				if (!world.isRemote && notSolid && !state.getMaterial().isLiquid()) {
					world.destroyBlock(pos, true);
				}
				SoundEvent soundevent = SoundEvents.ITEM_BUCKET_EMPTY;
				world.playSound(player, pos, soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState(), 11);
			}
			return true;
		}
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab == UsefulStuffs.TAB) {
			items.add(new ItemStack(this, 1, 0));
			items.add(new ItemStack(this, 1, 2));
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.getItemDamage() > 0) 
			tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.infinite_water.tooltip_1"));
		if (stack.getItemDamage() == 2)
			tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.infinite_water.tooltip_2_1"));
		else
			tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.infinite_water.tooltip_2"));
	}
	
}
