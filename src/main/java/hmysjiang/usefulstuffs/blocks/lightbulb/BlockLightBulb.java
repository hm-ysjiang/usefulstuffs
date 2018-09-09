package hmysjiang.usefulstuffs.blocks.lightbulb;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.blocks.BlockMaterials;
import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.items.ItemLightShooter;
import hmysjiang.usefulstuffs.utils.helper.InventoryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class BlockLightBulb extends Block {
	
	@SubscribeEvent
	public static void onPlayerPickUpItem(EntityItemPickupEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		ItemStack stack = event.getItem().getItem();
		if (stack.isItemEqual(new ItemStack(ModBlocks.light_bulb))) {
			ItemStack collector = InventoryHelper.findStackInPlayerInventory(player, new ItemStack(ModItems.light_shooter_collecter));
			if (!collector.isEmpty()) {
				stack.setCount(ItemLightShooter.incrAmmoCount(collector, stack.getCount()));
			}
		}
	}

	private static final double PIXEL = 0.0625D;	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(PIXEL*6, PIXEL*6, PIXEL*6, PIXEL*10, PIXEL*10, PIXEL*10);

	public BlockLightBulb() {
		super(BlockMaterials.LIGHTBULB);
		setUnlocalizedName(Reference.ModBlocks.LIGHT_BULB.getUnlocalizedName());
		setRegistryName(Reference.ModBlocks.LIGHT_BULB.getRegistryName());
		ModItems.itemblocks.add(new ItemBlock(this).setRegistryName(getRegistryName()));
		setLightLevel(1.0F);
		setSoundType(SoundType.CLOTH);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDING_BOX;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}
	
	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}

}
