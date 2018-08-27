package hmysjiang.usefulstuffs.items.baubles;

import java.util.List;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.utils.handler.KeyBindingHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStorageBag extends Item implements IBauble {
	
	public static void onKeyBindingPressed(EntityPlayer player, ItemStack bauble) {
		if (player.isSneaking()) {}
		else {
			if (!bauble.hasTagCompound() || !bauble.getTagCompound().hasKey("Current") || !bauble.getTagCompound().hasKey("Cont")) {
				//create a new handler, deal with the contents
				ItemStackHandler handler = new ItemStackHandler(36);
				IInventory inv = player.inventory;
				for (int j = 0 ; j<9 ; j++) {
					handler.setStackInSlot(j, inv.getStackInSlot(j));
					inv.removeStackFromSlot(j);
				}
				//seal the information into nbt
				NBTTagCompound compound = new NBTTagCompound();
				compound.setInteger("Current", 1);
				compound.setTag("Cont", handler.serializeNBT());
				bauble.setTagCompound(compound);
			}
			else {
				//retrieve the information from nbt
				NBTTagCompound compound = bauble.getTagCompound();
				ItemStackHandler handler = new ItemStackHandler(36);
				handler.deserializeNBT(compound.getCompoundTag("Cont"));
				int cur = compound.getInteger("Current");
				IInventory inv = player.inventory;
				//copy the items in player'shotbar into handler
				for (int j = 0 ; j<9 ; j++) {
					handler.setStackInSlot(cur * 9 + j, inv.getStackInSlot(j));
				}
				cur ++;	cur %= 4;
				for (int j = 0 ; j<9 ; j++) {
					ItemStack stack = handler.getStackInSlot(cur * 9 + j);
					if (stack == null) {
						inv.removeStackFromSlot(j);
					}
					else {
						inv.setInventorySlotContents(j, stack);
					}
				}
				//update the nbt data
				compound.setTag("Cont", handler.serializeNBT());
				compound.setInteger("Current", cur);
				bauble.setTagCompound(compound);
			}
		}
	}

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
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.storage_body.tooltip_1"));
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.storage_body.tooltip_2", KeyBindingHandler.keybindings.get(0).getDisplayName()));
	}

}
