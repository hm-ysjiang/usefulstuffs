package hmysjiang.usefulstuffs.items.baubles;

import java.util.List;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.cap.BaublesCapabilities;
import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayer.SleepResult;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class ItemPotatoCharm extends Item implements IBauble {
	
	@SubscribeEvent(priority=EventPriority.HIGH)
	public static void onPlayerTrySleep(PlayerSleepInBedEvent event) {
		if (ConfigManager.charmPotatoEnabled) {
			EntityPlayer player = event.getEntityPlayer();
			if (player.getCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null).getStackInSlot(6).getItem() == ModItems.charm_potato) {
				World world = player.world;
				if (world.isRemote)	return;
				if (world.playerEntities.size() > 1)
					return;
				if (player.world.isDaytime()) {
					BlockPos bedLocation = event.getPos();
					IBlockState state = world.getBlockState(bedLocation);
					EnumFacing enumfacing = state.getValue(BlockBed.FACING);
					
					if (player.isRiding()) {
						player.dismountRidingEntity();
					}
					
					player.spawnShoulderEntities();
					if (player.width != 0.2F || player.height != 0.2F) {
						float width = player.width;
						player.width = 0.2F;
						player.height = 0.2F;
						
						if (player.width < width) {
							double d = (double)0.2F / 2.0D;
							player.setEntityBoundingBox(new AxisAlignedBB(player.posX - d, player.posY, player.posZ - d, player.posX + d, player.posY + (double)player.height, player.posZ + d));
						}
						
						AxisAlignedBB axisalignedbb = player.getEntityBoundingBox();
						player.setEntityBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + (double)player.width, axisalignedbb.minY + (double)player.height, axisalignedbb.minZ + (double)player.width));
					
						if (player.width > width && !player.firstUpdate && !player.world.isRemote) {
							player.move(MoverType.SELF, (double)(width - player.width), 0.0D, (double)(width - player.width));
						}
					}
					
					if (enumfacing != null) {
						float f1 = 0.5F + (float)enumfacing.getFrontOffsetX() * 0.4F;
						float f = 0.5F + (float)enumfacing.getFrontOffsetZ() * 0.4F;
						player.setRenderOffsetForSleep(enumfacing);
						player.setPosition((double)((float)bedLocation.getX() + f1), (double)((float)bedLocation.getY() + 0.6875F), (double)((float)bedLocation.getZ() + f));
					}
					else {
						player.setPosition((double)((float)bedLocation.getX() + 0.5F), (double)((float)bedLocation.getY() + 0.6875F), (double)((float)bedLocation.getZ() + 0.5F));
					}
					
					player.sleeping = true;
					player.sleepTimer = 110;
					player.bedLocation = bedLocation;
					player.motionX = 0.0D;
					player.motionY = 0.0D;
					player.motionZ = 0.0D;
					
					world.updateAllPlayersSleepingFlag();
					
					event.setResult(SleepResult.OK);
					player.sendStatusMessage(new TextComponentTranslation("usefulstuffs.potato_charm.statushint"), true);
					player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation(new ResourceLocation("slowness").toString()), 200, 2));
					player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation(new ResourceLocation("mining_fatigue").toString()), 200));
					player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation(new ResourceLocation("blindness").toString()), 200));
					player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation(new ResourceLocation("hunger").toString()), 200));
					player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation(new ResourceLocation("weakness").toString()), 200, 2));
					player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation(new ResourceLocation("nausea").toString()), 200));
				}
			}
		}
	}
	
	public ItemPotatoCharm() {
		setRegistryName(Reference.ModItems.POTATO_CHARM.getRegistryName());
		setUnlocalizedName(Reference.ModItems.POTATO_CHARM.getUnlocalizedName());
		setMaxStackSize(1);
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.CHARM;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("usefulstuffs.potato_charm.tooltip"));
	}

}
