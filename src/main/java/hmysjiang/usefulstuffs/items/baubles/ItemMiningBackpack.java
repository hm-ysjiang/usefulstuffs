package hmysjiang.usefulstuffs.items.baubles;

import java.util.List;

import baubles.api.cap.BaublesCapabilities;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.client.gui.GuiHandler;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.utils.handler.KeyBindingHandler;
import hmysjiang.usefulstuffs.utils.helper.OreDictHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@EventBusSubscriber
public class ItemMiningBackpack extends ItemBackpack {
	
	public static void setDefaultTag(ItemStack stack) {
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		if (!stack.getTagCompound().hasKey("Cont")) {
			ItemStackHandler emptyHandler = new ItemStackHandler(54);
			stack.getTagCompound().setTag("Cont", emptyHandler.serializeNBT());
		}
		if (!stack.getTagCompound().hasKey("Filter")) {
			ItemStackHandler emptyHandler = new ItemStackHandler(18);
			stack.getTagCompound().setTag("Filter", emptyHandler.serializeNBT());
		}
		if (!stack.getTagCompound().hasKey("QuickDepo")) {
			stack.getTagCompound().setBoolean("QuickDepo", false);
		}
		if (!stack.getTagCompound().hasKey("Auto")) {
			stack.getTagCompound().setBoolean("Auto", true);
		}
	}
	
	public static void updateProperties(ItemStack stack, boolean auto, boolean quickDepo) {
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setBoolean("Auto", auto);
		stack.getTagCompound().setBoolean("QuickDepo", quickDepo);
	}
	
	@SubscribeEvent
	public static void onPlayerPickUp(EntityItemPickupEvent event) {
		if (event.getEntityPlayer() != null) {
			ItemStack stack = event.getItem().getItem().copy();
			EntityPlayer player = event.getEntityPlayer();
			if (player.getCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null).getStackInSlot(5).getItem() == ModItems.mining_backpack) {
				ItemStack backpack = player.getCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null).getStackInSlot(5);
				if (backpack.getTagCompound() == null) ItemMiningBackpack.setDefaultTag(backpack);
				if (backpack.getTagCompound().getBoolean("Auto")) {
					ItemStackHandler filter = new ItemStackHandler(18);
					filter.deserializeNBT(backpack.getTagCompound().getCompoundTag("Filter"));
					if (preCheck(stack, filter)) return;
					if (check(stack) || postCheck(stack, filter)) {
						ItemStackHandler handler = new ItemStackHandler(54);
						handler.deserializeNBT(backpack.getTagCompound().getCompoundTag("Cont"));
						for (int i = 0 ; i<handler.getSlots() ; i++) {
							stack = handler.insertItem(i, stack.copy(), false);
							if (stack.getCount() == 0) {
								break;
							}
						}
						event.getItem().getItem().setCount(stack.getCount());
						backpack.getTagCompound().setTag("Cont", handler.serializeNBT());
						return;
					}
				}
			}
		}
	}

	private static boolean preCheck(ItemStack stack, ItemStackHandler filter) {
		for (int i = 9 ; i<filter.getSlots() ; i++)
			if (stack.isItemEqual(filter.getStackInSlot(i)))
				return true;
		return false;
	}

	private static boolean check(ItemStack stack) {
		if (OreDictHelper.isRegisterOre(stack))
			return true;
		String registryName = stack.getItem().getRegistryName().getResourcePath();
		if (registryName.endsWith("ore") || registryName.endsWith("crystal"))
			return true;
		return false;
	}
	
	private static boolean postCheck(ItemStack stack, ItemStackHandler filter) {
		for (int i = 0 ; i<9 ; i++)
			if (stack.isItemEqual(filter.getStackInSlot(i)))
				return true;
		return false;
	}

	public ItemMiningBackpack() {
		super(Reference.ModItems.BODY_BACKPACK_MINING.getUnlocalizedName(), Reference.ModItems.BODY_BACKPACK_MINING.getRegistryName());
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Cont"))
			setDefaultTag(stack);
		if (handIn == EnumHand.MAIN_HAND && playerIn.isSneaking()) {
			if (!worldIn.isRemote)
				playerIn.openGui(UsefulStuffs.instance, GuiHandler.GUI_BACKPACK_MINING_CONFIG, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player.isSneaking() && worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
			ItemStack backpack = player.getHeldItem(hand);
			if (!backpack.hasTagCompound()) setDefaultTag(backpack);
			if (backpack.getTagCompound().getBoolean("QuickDepo")) {
				IItemHandler handler = worldIn.getTileEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				ItemStackHandler backpackCont = new ItemStackHandler(54);
				backpackCont.deserializeNBT(backpack.getTagCompound().getCompoundTag("Cont"));
				for (int i = 0 ; i<backpackCont.getSlots() ; i++) {
					ItemStack stack = backpackCont.extractItem(i, 64, false);
					if (stack.isEmpty()) continue;
					for (int j = 0 ; j<handler.getSlots() ; j++) {
						stack = handler.insertItem(j, stack.copy(), false);
						if (stack.isEmpty()) break;
					}
					if (!stack.isEmpty()) {
						backpackCont.insertItem(i, stack, false);
						break;
					}
				}
				backpack.getTagCompound().setTag("Cont", backpackCont.serializeNBT());
			}
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("usefulstuffs.backpack.tooltip_mining_1"));
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.backpack.tooltip_mining_2"));
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.backpack.tooltip", KeyBindingHandler.keybindings.get(0).getDisplayName()));
	}
	
}
