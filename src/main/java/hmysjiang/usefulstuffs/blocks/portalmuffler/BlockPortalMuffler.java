package hmysjiang.usefulstuffs.blocks.portalmuffler;

import java.util.List;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPortalMuffler extends Block implements ITileEntityProvider {
	
	public BlockPortalMuffler() {
		super(new Material(MapColor.CLOTH));
		setUnlocalizedName(Reference.ModBlocks.PORTAL_MUFFLER.getUnlocalizedName());
		setRegistryName(Reference.ModBlocks.PORTAL_MUFFLER.getRegistryName());
		ModItems.itemblocks.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityPortalMuffler();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(I18n.format("usefulstuffs.portal_muffler.tooltip_1"));
		tooltip.add(TextFormatting.GOLD + I18n.format("usefulstuffs.portal_muffler.tooltip_2"));
	}

}
