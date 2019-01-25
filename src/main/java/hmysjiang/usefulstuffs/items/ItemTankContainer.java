package hmysjiang.usefulstuffs.items;

import java.util.List;

import javax.annotation.Nullable;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.utils.capabilities.CapabilityFluidItemStack;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.wrappers.BlockLiquidWrapper;
import net.minecraftforge.fluids.capability.wrappers.FluidBlockWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTankContainer extends Item {
	
	public ItemTankContainer() {
		setRegistryName(Reference.ModItems.TANK_CONTAINER.getRegistryName());
		setUnlocalizedName(Reference.ModItems.TANK_CONTAINER.getUnlocalizedName());
		setMaxStackSize(1);
		setHasSubtypes(true);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			(new FluidStack(FluidRegistry.WATER, 0)).writeToNBT(stack.getTagCompound());
			stack.getTagCompound().setBoolean("Place", false);
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (playerIn.isSneaking()) {
			if (!worldIn.isRemote) {
				ItemStack tank = playerIn.getHeldItem(handIn);
				if (!tank.hasTagCompound()) {
					tank.setTagCompound(new NBTTagCompound());
				}
				if (!tank.getTagCompound().hasKey("Place")) {
					tank.getTagCompound().setBoolean("Place", false);
				}
				tank.getTagCompound().setBoolean("Place", !tank.getTagCompound().getBoolean("Place"));
			}
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}
		else {
			return new ActionResult<ItemStack>(execute(playerIn, worldIn, handIn), playerIn.getHeldItem(handIn));
		}
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos posIn, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		return execute(player, worldIn, hand);
	}
	
	private EnumActionResult execute(EntityPlayer player, World worldIn, EnumHand hand) {
		ItemStack tank = player.getHeldItem(hand);
		boolean place = isPlaceMode(tank);
		RayTraceResult raytraceresult = this.rayTrace(worldIn, player, !place);
		if (raytraceresult == null)	
			return EnumActionResult.PASS;
		if (raytraceresult.typeOfHit != Type.BLOCK) 
			return EnumActionResult.PASS;
		BlockPos pos = raytraceresult.getBlockPos();
		if (worldIn.isBlockModifiable(player, pos)) {
			if (place) {
				
				//Try to access the fluid handler
				RayTraceResult ray2 = this.rayTrace(worldIn, player, true);
				if (worldIn.getTileEntity(ray2.getBlockPos()) != null && worldIn.getTileEntity(ray2.getBlockPos()).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, ray2.sideHit)) {
					IFluidHandler handler = worldIn.getTileEntity(ray2.getBlockPos()).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, ray2.sideHit);
					FluidStack fluid = getFluid(tank);
					if (tank != null) {
						int toFill = handler.fill(fluid.copy(), false);
						if (toFill > 0) {
							handler.fill(new FluidStack(fluid, toFill), true);
							fluid.amount -= toFill;
							fluid.writeToNBT(tank.getTagCompound());
							if (worldIn.isRemote)
								worldIn.playSound(player, pos, fluid.getFluid().getEmptySound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
							return EnumActionResult.SUCCESS;
						}
					}
				}
				
				boolean replace = worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
				pos = replace && raytraceresult.sideHit == EnumFacing.UP ? pos : pos.offset(raytraceresult.sideHit);
				if (!player.canPlayerEdit(pos, raytraceresult.sideHit, tank)) 
					return EnumActionResult.PASS;
				else if (this.placeFluid(worldIn, pos, player, tank)) {
					return EnumActionResult.SUCCESS;
				}
				return EnumActionResult.PASS;
			}
			else {
				if (!player.canPlayerEdit(pos.offset(raytraceresult.sideHit), raytraceresult.sideHit, tank)) 
					return EnumActionResult.PASS;
				IBlockState state = worldIn.getBlockState(pos);
				IFluidHandler fluid = null;
				if (state.getBlock() instanceof BlockLiquid) {
					fluid = new BlockLiquidWrapper((BlockLiquid) state.getBlock(), worldIn, pos);
				}
				else if (state.getBlock() instanceof IFluidBlock) {
					fluid = new FluidBlockWrapper((IFluidBlock) state.getBlock(), worldIn, pos);
				}
				if (fluid != null) {
					FluidStack drain = fluid.drain(1000, false);
					if (drain != null && drain.amount == 1000) {
						if (fillTank(tank, drain)) {
							fluid.drain(1000, true);
							worldIn.playSound(player, pos, drain.getFluid().getFillSound(drain), SoundCategory.BLOCKS, 1.0F, 1.0F);
							return EnumActionResult.SUCCESS;
						}
					}
				}
				return EnumActionResult.PASS;
			}
		}
		return EnumActionResult.PASS;
	}

	private boolean placeFluid(World worldIn, BlockPos pos, EntityPlayer player, ItemStack tank) {
		if (!tank.hasTagCompound()) {
			tank.setTagCompound(new NBTTagCompound());
		}
		FluidStack fluid = getFluid(tank);
		if (fluid == null || fluid.amount < 1000)
			return false;
		IBlockState state = worldIn.getBlockState(pos);
		boolean notSolid = !state.getMaterial().isSolid();

		if (!worldIn.isAirBlock(pos) && !notSolid) {
			return false;
		}
		if (state.getBlock() instanceof IFluidBlock || state.getBlock() instanceof BlockLiquid)
			return false;
		else if (fluid.getFluid().canBePlacedInWorld()) {
			if (fluid.getFluid().doesVaporize(fluid) && worldIn.provider.doesWaterVaporize()) {
				int x = pos.getX();
				int y = pos.getY();
				int z = pos.getZ();
				worldIn.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);

				for (int i = 0; i < 8; ++i) {
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double) x + Math.random(), (double) i + Math.random(), (double) z + Math.random(), 0.0D, 0.0D, 0.0D);
				}
			}
			else {
				if (!worldIn.isRemote && notSolid && !state.getMaterial().isLiquid()) {
					worldIn.destroyBlock(pos, true);
				}
				worldIn.playSound(player, pos, fluid.getFluid().getFillSound(fluid), SoundCategory.BLOCKS, 1.0F, 1.0F);
				worldIn.setBlockState(pos, fluid.getFluid().getBlock().getDefaultState(), 11);
				if (pos.getY() > 0) {
					worldIn.neighborChanged(pos, fluid.getFluid().getBlock(), pos.down());
				}
			}
			fluid.amount -= 1000;
			fluid.writeToNBT(tank.getTagCompound());
			return true;
		}
		return false;
	}
	
	private boolean fillTank(ItemStack tank, FluidStack fluidStack) {
		if (!tank.hasTagCompound()) {
			tank.setTagCompound(new NBTTagCompound());
		}
		FluidStack fluid = FluidStack.loadFluidStackFromNBT(tank.getTagCompound());
		if (fluid == null) {
			fluid = fluidStack.copy();
			fluid.amount = 1000;
			fluid.writeToNBT(tank.getTagCompound());
			return true;
		}
		if (fluidStack.isFluidEqual(fluid)) {
			if (getCapacity(tank) - fluid.amount >= 1000) {
				fluid.amount += 1000;
				fluid.writeToNBT(tank.getTagCompound());
				return true;
			}
		}
		else if (fluid.amount == 0) {
			FluidStack newFluid = fluidStack.copy();
			newFluid.amount = 1000;
			newFluid.writeToNBT(tank.getTagCompound());
			return true;
		}
		return false;
	}

	private boolean isPlaceMode(ItemStack tank) {
		if (!tank.hasTagCompound()) {
			tank.setTagCompound(new NBTTagCompound());
		}
		if (!tank.getTagCompound().hasKey("Place")) {
			tank.getTagCompound().setBoolean("Place", false);
		}
		return tank.getTagCompound().getBoolean("Place");
	}

//	@Override
//	public int getRGBDurabilityForDisplay(ItemStack stack) {
//		if (!stack.hasTagCompound())
//			stack.setTagCompound(new NBTTagCompound());
//		FluidStack fluid = FluidStack.loadFluidStackFromNBT(stack.getTagCompound());
//		if (fluid == null)
//			return super.getRGBDurabilityForDisplay(stack);
//		LogHelper.info(fluid.getFluid().getColor(fluid) & 0xFFFFFF);
//		return fluid.getFluid().getColor(fluid) & 0xFFFFFF;
//	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		FluidStack fluid = FluidStack.loadFluidStackFromNBT(stack.getTagCompound());
		int cap = getCapacity(stack), amount = (fluid == null ? 0 : fluid.amount);
		return 1 -(((double) amount) / ((double) cap));
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab == UsefulStuffs.TAB) {
			for (TankTier tier: TankTier.values()) {
				if (tier.availiable())
					items.add(new ItemStack(this, 1, tier.getMeta()));
			}	
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Place")) {
			if (stack.getTagCompound().getBoolean("Place")) {
				return getUnlocalizedName() + "_" + TankTier.byMeta(stack.getMetadata()).getName() + "_place";
			}
			return getUnlocalizedName() + "_" + TankTier.byMeta(stack.getMetadata()).getName() + "_fill";
		}
		return getUnlocalizedName() + "_" + TankTier.byMeta(stack.getMetadata()).getName();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.tank_container.tooltip_1"));
		if (GuiScreen.isShiftKeyDown()) {
			if (stack.hasTagCompound()) {
				if (stack.getTagCompound().hasKey("FluidName")) {
					FluidStack fluid = FluidStack.loadFluidStackFromNBT(stack.getTagCompound());
					if (fluid != null) {
						tooltip.add(I18n.format("usefulstuffs.tank_container.tooltip_2", fluid.amount > 0 ? fluid.getLocalizedName() : "None"));
						tooltip.add(I18n.format("usefulstuffs.tank_container.tooltip_3", fluid.amount, getCapacity(stack)));
					}
					else {
						tooltip.add(I18n.format("usefulstuffs.tank_container.tooltip_2", "None"));
						tooltip.add(I18n.format("usefulstuffs.tank_container.tooltip_3", 0, getCapacity(stack)));
					}
				}
				if (stack.getTagCompound().hasKey("Place"))
					tooltip.add(I18n.format("usefulstuffs.tank_container.tooltip_4", stack.getTagCompound().getBoolean("Place") ? "Place" : "Fill"));
			}
		}
		else {
			tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.details.tooltip"));
		}
	}
	
	@Nullable
	public static FluidStack getFluid(ItemStack stack) {
		if (stack.hasTagCompound()) {
			return FluidStack.loadFluidStackFromNBT(stack.getTagCompound());
		}
		return null;
	}
	
	public static void setFluid(ItemStack stack, @Nullable FluidStack fluid) {
		if (fluid != null) {
			if (!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			fluid.writeToNBT(stack.getTagCompound());
		}
	}
	
	public static int getCapacity(ItemStack stack) {
		return TankTier.getCapacity(stack.getMetadata());
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new CapabilityFluidItemStack(stack);
	}
	
	public static enum TankTier implements IStringSerializable{
		BASIC(0, 16000, "basic"),
		BETTER(1, 32000, "better"),
		ADVANCED(2, 64000, "advanced"),
		REINFORCED(3, 128000, "reinforced"),
		EXTRAODINARY(4, 256000, "extraordinary"),
		NONE(5, 0, "none");
		
		private static TankTier[] META_LOOKUP = new TankTier[6];
		
		private final int meta;
		private final int capacity;
		private final String name;
		
		private TankTier(int meta, int capacity, String name) {
			this.meta = meta;
			this.capacity = capacity;
			this.name = name;
		}

		@Override
		public String getName() {
			return this.name;
		}
		
		public int getMeta() {
			return meta;
		}
		
		public int getCapacity() {
			return capacity;
		}
		
		public boolean availiable() {
			return this.meta != 5;
		}
		
		public static TankTier byMeta(int meta) {
			return META_LOOKUP[meta];
		}
		
		public static int getCapacity(int meta) {
			return byMeta(meta).getCapacity();
		}
		
		static {
			for (TankTier tier: TankTier.values()) {
				META_LOOKUP[tier.meta] = tier;
			}
		}
		
	}
	
}
