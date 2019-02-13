package hmysjiang.usefulstuffs.utils;

import java.util.Collection;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TileEntityRotatable extends TileEntity {
	
	// Prevent tile got reset when being rotated
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		// Refresh when blocks is different
		if (oldState.getBlock() != newState.getBlock())
			return true;
		
		if (oldState == newState)
			return false;

		Collection<IProperty<?>> propertyKeys = oldState.getPropertyKeys();
		Collection<IProperty<?>> newPropertyKeys = newState.getPropertyKeys();
		// Refresh if properties are different
		for (IProperty<?> property: newPropertyKeys) {
			if (!propertyKeys.contains(property)) 
				return true;
		}
		for (IProperty<?> property: propertyKeys) {
			// Refresh if properties are different
			if (!newPropertyKeys.contains(property)) 
				return true;
			// Refresh if one of the properties changed
			if (!(property.getName().equals("facing") || property.getName().equals("rotation"))) {
				if (oldState.getValue(property) != newState.getValue(property))
					return true;
			}
		}
		
		return false;
	}
	
}
