package hmysjiang.usefulstuffs.utils.capabilities;

import hmysjiang.usefulstuffs.items.ItemTankContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class CapabilityFluidItemStack implements ICapabilityProvider {
	
	final ItemStack stack;
	
	public CapabilityFluidItemStack(ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return getCapability(capability, facing) != null;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY) {
			return (T) new IFluidHandlerItem() {
				
				@Override
				public IFluidTankProperties[] getTankProperties() {
					return new IFluidTankProperties[] {new FluidTankProperties(ItemTankContainer.getFluid(stack), ItemTankContainer.getCapacity(stack))};
				}
				
				@Override
				public int fill(FluidStack resource, boolean doFill) {
					if (resource == null) 
						return 0;
					if (ItemTankContainer.getFluid(stack) == null || ItemTankContainer.getFluid(stack).isFluidEqual(resource)) {
						FluidStack destination = ItemTankContainer.getFluid(stack);
						int cap = ItemTankContainer.getCapacity(stack);
						if (destination != null) {
							int space = cap - destination.amount;
							if (doFill) {
								destination.amount += resource.amount > space ? space : resource.amount;
								ItemTankContainer.setFluid(stack, destination);
							}
							return resource.amount > space ? space : resource.amount;
						}
						if (doFill) {
							FluidStack newFluid = resource.copy();
							newFluid.amount = resource.amount > cap ? cap : resource.amount;
							ItemTankContainer.setFluid(stack, newFluid);
						}
						return resource.amount > cap ? cap : resource.amount;
					}
					else if (ItemTankContainer.getFluid(stack).amount == 0) {
						if (doFill) {
							FluidStack newFluid = resource.copy();
							ItemTankContainer.setFluid(stack, resource);
						}
						return resource.amount;
					}
					return 0;
				}
				
				@Override
				public FluidStack drain(int maxDrain, boolean doDrain) {
					FluidStack fluid = FluidStack.loadFluidStackFromNBT(stack.getTagCompound());
					if (fluid == null)
						return null;
					int amount = fluid.amount;
					fluid.amount = maxDrain > fluid.amount ? fluid.amount : maxDrain;
					if (doDrain) {
						ItemTankContainer.setFluid(stack, new FluidStack(fluid, amount - fluid.amount));
					}
					return fluid;
				}
				
				@Override
				public FluidStack drain(FluidStack resource, boolean doDrain) {
					if (ItemTankContainer.getFluid(stack).isFluidEqual(resource))
						return drain(resource.amount, doDrain);
					return null;
				}
				
				@Override
				public ItemStack getContainer() {
					return stack;
				}
			};
		}
		return null;
	}

}
