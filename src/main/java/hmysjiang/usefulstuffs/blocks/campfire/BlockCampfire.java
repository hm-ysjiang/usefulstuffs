package hmysjiang.usefulstuffs.blocks.campfire;

import java.util.List;
import java.util.Random;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.blocks.BlockMaterials;
import hmysjiang.usefulstuffs.client.gui.GuiHandler;
import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

@EventBusSubscriber
public class BlockCampfire extends Block implements ITileEntityProvider {
	
	public static boolean needFuel;
	
	@SubscribeEvent
	public static void onInteract(PlayerInteractEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		if (player.isSneaking() && player.getHeldItemMainhand() != null && player.getHeldItemMainhand().isItemEqualIgnoreDurability(new ItemStack(Items.FLINT_AND_STEEL))) {
			if (player.world.getBlockState(event.getPos()) == Blocks.LOG.getDefaultState()) {
				if (!player.capabilities.isCreativeMode) {
					ItemStack stack = player.getHeldItemMainhand();
					if (stack.getItemDamage() >= stack.getMaxDamage()) {
						for (int i = 0 ; i<player.inventory.getSizeInventory() ; i++) {
							if (player.inventory.getStackInSlot(i) == stack) {
								player.inventory.removeStackFromSlot(i);
							}
						}
					}
					else { 
						stack.setItemDamage(stack.getItemDamage()+1);
					}
				}
				player.world.setBlockState(event.getPos(), ModBlocks.campfire.getDefaultState());
				event.setCanceled(true);
			}
		}
	}

	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625, 0, 0.0625, 0.0625*15, 0.0625*4, 0.0625*15);
	private Random rnd = new Random();

	public BlockCampfire() {
		super(new BlockMaterials.Campfire());
		setRegistryName(Reference.ModBlocks.CAMPFIRE.getRegistryName());
		setUnlocalizedName(Reference.ModBlocks.CAMPFIRE.getUnlocalizedName());
		ModItems.itemblocks.add(new ItemBlock(this).setRegistryName(getRegistryName()));
		setLightLevel(1.0F);
		setSoundType(SoundType.WOOD);
		setHardness(0.5F);
		
		needFuel = ConfigManager.campfireNeedsFuel;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (playerIn.isSneaking()) {
			if (!worldIn.isRemote) {
				if (needFuel) {
					playerIn.openGui(UsefulStuffs.instance, GuiHandler.GUI_CAMPFIRE, worldIn, pos.getX(), pos.getY(), pos.getZ());
				}
				else {
					playerIn.sendMessage(new TextComponentString("Radius: " + ((TileEntityCampfire) worldIn.getTileEntity(pos)).getBuffRadius()));
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (worldIn.getTileEntity(pos) != null) {
			IItemHandler handler;
			if (needFuel)
				handler = ((TileEntityCampfire)worldIn.getTileEntity(pos)).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
			else 
				handler = ((TileEntityCampfire)worldIn.getTileEntity(pos)).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			for (int i = 0 ; i<handler.getSlots() ; i++)
				if (!handler.getStackInSlot(i).isEmpty())
					worldIn.spawnEntity(new EntityItem(worldIn, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, handler.getStackInSlot(i)));
		}
		worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.STICK, rnd.nextInt(2)+2)));
		super.breakBlock(worldIn, pos, state);
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
		return BlockRenderLayer.CUTOUT;
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
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCampfire();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, worldIn, tooltip, advanced);
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.campfire.tooltip_1"));
		tooltip.add(TextFormatting.YELLOW + I18n.format("usefulstuffs.campfire.tooltip_2"));
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
