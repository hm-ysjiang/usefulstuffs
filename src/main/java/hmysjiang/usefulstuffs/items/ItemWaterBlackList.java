package hmysjiang.usefulstuffs.items;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.utils.IItemBindable;
import hmysjiang.usefulstuffs.utils.WorldHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWaterBlackList extends Item implements IItemBindable {
	
	public ItemWaterBlackList() {
		setUnlocalizedName(Reference.ModItems.WATERFILTER.getUnlocalizedName());
		setRegistryName(Reference.ModItems.WATERFILTER.getRegistryName());
		setMaxStackSize(1);
	}

	@Override
	public NBTTagList getBoundBlockListFromNBT(ItemStack stack) {
		return (stack.hasTagCompound() && stack.getTagCompound().hasKey("Blocks")) ? stack.getTagCompound().getTagList("Blocks", Constants.NBT.TAG_INT_ARRAY) : new NBTTagList() ;
	}

	@Override
	public void addBlockToListInNBT(World world, BlockPos pos, ItemStack stack, EnumFacing side) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Blocks")) {
			NBTTagList list = stack.getTagCompound().getTagList("Blocks", Constants.NBT.TAG_INT_ARRAY);
			int[] arr = new int[] {world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), side.getIndex()};
			for (int i = 0 ; i<list.tagCount() ; i++) 
				if (list.getIntArrayAt(i)[0] == arr[0] && list.getIntArrayAt(i)[1] == arr[1] && list.getIntArrayAt(i)[2] == arr[2] && list.getIntArrayAt(i)[3] == arr[3] && list.getIntArrayAt(i)[4] == arr[4])
					return;
			NBTTagIntArray data = new NBTTagIntArray(arr);
			list.appendTag(data);
			stack.getTagCompound().setTag("Blocks", list);
		}
		else {
			NBTTagCompound compound = new NBTTagCompound();
			NBTTagList list = new NBTTagList();
			int[] arr = new int[] {world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), side.getIndex()};
			NBTTagIntArray data = new NBTTagIntArray(arr);
			list.appendTag(data);
			compound.setTag("Blocks", list);
			stack.setTagCompound(compound);
		}
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (playerIn.isSneaking() && worldIn.getBlockState(pos) != ModBlocks.well.getDefaultState()) {
			addBlockToListInNBT(worldIn, pos, playerIn.getHeldItem(hand), facing);
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.waterfilter.tooltip_1"));
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.waterfilter.tooltip_2"));
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.waterfilter.tooltip_3"));
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Blocks")) {
			if (GuiScreen.isShiftKeyDown()) {
				tooltip.add("------");
				NBTTagList list = getBoundBlockListFromNBT(stack);
				for (int i = 0 ; i<list.tagCount() ; i++) { 
					int[] data = list.getIntArrayAt(i);
					World world = WorldHelper.getWorldFromId(data[0]);
					String blockname = world.getBlockState(new BlockPos(data[1], data[2], data[3])).getBlock().getUnlocalizedName();
					EnumFacing side = EnumFacing.DOWN;
					for (EnumFacing facing:EnumFacing.values())
						if (facing.getIndex() == data[4])
							side = facing;
					tooltip.add(side.name()+" of Block "+blockname+"("+data[1]+", "+data[2]+", "+data[3]+") in dimension id "+data[0]);
					if (i != list.tagCount()-1)
						tooltip.add("------");
				}
			}
			else 
				tooltip.add(TextFormatting.WHITE + I18n.format("usefulstuffs.details.tooltip"));
		}
		super.addInformation(stack, worldIn, tooltip, advanced);
	}
	
}