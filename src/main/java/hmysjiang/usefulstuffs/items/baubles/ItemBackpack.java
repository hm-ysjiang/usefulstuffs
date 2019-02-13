package hmysjiang.usefulstuffs.items.baubles;

import java.util.List;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.client.gui.GuiBackpack;
import hmysjiang.usefulstuffs.client.gui.GuiHandler;
import hmysjiang.usefulstuffs.container.ContainerItem;
import hmysjiang.usefulstuffs.utils.handler.KeyBindingHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public class ItemBackpack extends Item implements IBauble {
	
	protected static void setDefaultTag(ItemStack stack) {
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		if (!stack.getTagCompound().hasKey("Cont")) {
			ItemStackHandler emptyHandler = new ItemStackHandler(54);
			stack.getTagCompound().setTag("Cont", emptyHandler.serializeNBT());
		}
	}

	public static void onKeyBindingPressed(EntityPlayer player) {
		if (!player.world.isRemote)
			player.openGui(UsefulStuffs.instance, GuiHandler.GUI_BACKPACK_BAUBLE, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
	}
	
	public ItemBackpack(String unlocalizedName, String registryName) {
		setUnlocalizedName(unlocalizedName);
		setRegistryName(registryName);
		setMaxStackSize(1);
	}
	
	public ItemBackpack() {
		this(Reference.ModItems.BODY_BACKPACK.getUnlocalizedName(), Reference.ModItems.BODY_BACKPACK.getRegistryName());
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.BODY;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Cont"))
			setDefaultTag(stack);
		if (handIn == EnumHand.MAIN_HAND) {
			if (!worldIn.isRemote) {
				playerIn.openGui(UsefulStuffs.instance, GuiHandler.GUI_BACKPACK, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
			}
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("usefulstuffs.backpack.tooltip_normal"));
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.backpack.tooltip", KeyBindingHandler.keybindings.get(0).getDisplayName()));
		if (Minecraft.getMinecraft().currentScreen instanceof GuiBackpack && Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND) != stack) {
			ItemStackHandler handler = new ItemStackHandler(54);
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Cont"))
				handler.deserializeNBT(stack.getTagCompound().getCompoundTag("Cont"));
			boolean flag = false;
			for (int i = 0 ; i<handler.getSlots() ; i++) {
				if (handler.getStackInSlot(i).getItem() instanceof ItemBackpack) {
					flag = true;
					break;
				}
			}
			if (flag)
				tooltip.add(TextFormatting.RED + I18n.format("usefulstuffs.backpack.tooltip_blocked"));
		}
	}
	
}
