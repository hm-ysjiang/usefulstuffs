package hmysjiang.usefulstuffs.items.baubles;

import java.util.List;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class ItemStabilizingRing extends Item implements IBauble {
	
	@SubscribeEvent
	public static void onCalcSpeed(PlayerEvent.BreakSpeed event) {
		EntityPlayer player = event.getEntityPlayer();
		if (player != null && !player.onGround) {
			IBaublesItemHandler handler = player.getCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null);
			if (handler.getStackInSlot(1).isItemEqual(new ItemStack(ModItems.ring_stablizing)) || handler.getStackInSlot(2).isItemEqual(new ItemStack(ModItems.ring_stablizing)))
				event.setNewSpeed(event.getOriginalSpeed() * 5.0F);
		}
	}
	
	public ItemStabilizingRing() {
		setRegistryName(Reference.ModItems.STABILIZING_RING.getRegistryName());
		setUnlocalizedName(Reference.ModItems.STABILIZING_RING.getUnlocalizedName());
		setMaxStackSize(1);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("usefulstuffs.stabilizing_ring.tooltip"));
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.RING;
	}

}
