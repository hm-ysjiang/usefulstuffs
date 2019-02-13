package hmysjiang.usefulstuffs.items;

import java.util.List;

import javax.annotation.Nullable;

import hmysjiang.usefulstuffs.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFortressFinder extends Item {
	
	public ItemFortressFinder() {
		setRegistryName(Reference.ModItems.FORTRESS_FINDER.getRegistryName());
		setUnlocalizedName(Reference.ModItems.FORTRESS_FINDER.getUnlocalizedName());
		setMaxStackSize(1);

		this.addPropertyOverride(new ResourceLocation("angle"), new IItemPropertyGetter() {
			
			@SideOnly(Side.CLIENT)
			double rotation;
			@SideOnly(Side.CLIENT)
			double rota;
			@SideOnly(Side.CLIENT)
			long lastUpdateTick;
			
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				if (entityIn == null && !stack.isOnItemFrame()) {
					return 0.0F;
				}
				else {
					boolean flag = entityIn != null;
					Entity entity = (Entity) (flag ? entityIn : stack.getItemFrame());

					if (worldIn == null) {
						worldIn = entity.world;
					}

					double d0;

					if (worldIn.provider.isNether()) {
						double d1 = flag ? (double) entity.rotationYaw : this.getFrameRotation((EntityItemFrame) entity);
						d1 = MathHelper.positiveModulo(d1 / 360.0D, 1.0D);
						double d2 = this.toAngle(stack, entity) / (Math.PI * 2D);
						d0 = 0.5D - (d1 - 0.25D - d2);
					}
					else {
						d0 = Math.random();
					}

					if (flag) {
						d0 = this.wobble(worldIn, d0);
					}

					return MathHelper.positiveModulo((float) d0, 1.0F);
				}
			}
			
			@SideOnly(Side.CLIENT)
			private double wobble(World worldIn, double p_185093_2_) {
				if (worldIn.getTotalWorldTime() != this.lastUpdateTick) {
					this.lastUpdateTick = worldIn.getTotalWorldTime();
					double d0 = p_185093_2_ - this.rotation;
					d0 = MathHelper.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
					this.rota += d0 * 0.1D;
					this.rota *= 0.8D;
					this.rotation = MathHelper.positiveModulo(this.rotation + this.rota, 1.0D);
				}

				return this.rotation;
			}
			
			@SideOnly(Side.CLIENT)
			private double getFrameRotation(EntityItemFrame frameEntity) {
				return (double) MathHelper.wrapDegrees(180 + frameEntity.facingDirection.getHorizontalIndex() * 90);
			}
			
			@SideOnly(Side.CLIENT)
			private double toAngle(ItemStack stack, Entity entity) {
				if (stack.hasTagCompound() && stack.getTagCompound().hasKey("x")) {
					BlockPos blockpos = new BlockPos(stack.getTagCompound().getInteger("x"), stack.getTagCompound().getInteger("y"), stack.getTagCompound().getInteger("z"));
					return Math.atan2((double) blockpos.getZ() - entity.posZ, (double) blockpos.getX() - entity.posX);
				}
				return Math.random();
			}
		});
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (!worldIn.isRemote && worldIn.provider.isNether()) {
			BlockPos pos = ((ChunkProviderServer)worldIn.getChunkProvider()).getNearestStructurePos(worldIn, "Fortress", playerIn.getPosition(), true);
			ItemStack stack = playerIn.getHeldItem(handIn);
			if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("x")) {
				stack.setTagCompound(new NBTTagCompound());
			}
			stack.getTagCompound().setInteger("x", pos.getX());
			stack.getTagCompound().setInteger("y", pos.getY());
			stack.getTagCompound().setInteger("z", pos.getZ());
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("usefulstuffs.fortress_finder.tooltip_1"));
		tooltip.add(TextFormatting.AQUA + I18n.format("usefulstuffs.fortress_finder.tooltip_2"));
	}

}