package hmysjiang.usefulstuffs.items.baubles;

import java.util.List;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.utils.KeyBindingHandler;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemStorageBag extends Item implements IBauble {

	public ItemStorageBag() {
		setUnlocalizedName(Reference.ModItems.BODY_STORAGE.getUnlocalizedName());
		setRegistryName(Reference.ModItems.BODY_STORAGE.getRegistryName());
		setMaxStackSize(1);
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.BODY;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		IBaublesItemHandler handler = playerIn.getCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null);
		ItemStack stack1 = playerIn.getHeldItem(handIn);
		playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, handler.getStackInSlot(5));
		handler.setStackInSlot(5, stack1);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.storage_body.tooltip_1"));
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.storage_body.tooltip_2", KeyBindingHandler.keybindings.get(0).getDisplayName()));
	}

}
