package hmysjiang.usefulstuffs.miscs;

import javax.annotation.Nullable;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class WaterTank extends FluidTank {
	
	private static final Fluid WATER = FluidRegistry.WATER;

    public WaterTank(int capacity)
    {
        super(capacity);
    }
    
    @Override
    public int fill(FluidStack resource, boolean doFill) {
    	if (resource.getFluid() == WATER)
    		return super.fill(resource, doFill);
    	return resource.amount;
    }
	
}