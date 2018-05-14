package hmysjiang.usefulstuffs.items;

import java.util.ArrayList;
import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.UsefulStuffs;
import hmysjiang.usefulstuffs.handlers.AchievementHandler;
import hmysjiang.usefulstuffs.handlers.EnumHandler.ExcaliburType;
import hmysjiang.usefulstuffs.init.ModDamageSources;
import hmysjiang.usefulstuffs.miscs.ExplosionUnharmful;
import hmysjiang.usefulstuffs.miscs.helpers.MathHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class ItemExcalibur extends ItemSword {
	
	/***
	 * The time for player to fully charge the sword
	 */
	private static final int FULLCHARGETICK = 3*10;
	private static final int ATTACKRANGE = 32;
	
	private int chargeTicks;
	private List<BlockPos> affectedBlockPositions;
	private List<Entity> affectedEntityList;
	

	public ItemExcalibur() {
		super(EnumHelper.addToolMaterial(Reference.MOD_ID + "excalibur", 0, 0, 0F, 7.5F, 14));
		setUnlocalizedName(Reference.ModItems.EXCALIBUR.getUnlocalizedName());
		setRegistryName(Reference.ModItems.EXCALIBUR.getRegistryName());
		this.setHasSubtypes(true);
		setMaxDamage(0);
		
		this.chargeTicks = 0;
		this.affectedBlockPositions = new ArrayList<BlockPos>();
		this.affectedEntityList = new ArrayList<Entity>();
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for (int i = 0;i<ExcaliburType.values().length;i++) {
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		for (int i = 0;i<ExcaliburType.values().length;i++) 
			if (stack.getItemDamage() == i)
				return this.getUnlocalizedName() + "." + ExcaliburType.values()[i].getName();
		return this.getUnlocalizedName() + "." + ExcaliburType.UNCHARGED.getName();
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if (!(playerIn.hasAchievement(AchievementHandler.achievementExcalibur)))
			playerIn.addStat(AchievementHandler.achievementExcalibur);
		super.onCreated(stack, worldIn, playerIn);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(I18n.format("usefulstuffs.excalibur.tooltip"));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
		if (!worldIn.isRemote) {
			if (hand == EnumHand.MAIN_HAND && playerIn.isSneaking()) {
				if (this.chargeTicks == FULLCHARGETICK && itemStackIn.getItemDamage() == 0) {
					itemStackIn = new ItemStack(itemStackIn.getItem(), 1, 1);
				}
				if (this.chargeTicks<FULLCHARGETICK) 
					this.chargeTicks++;
			}
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand); 
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		World world = entityLiving.getEntityWorld();
		if (!world.isRemote) {
			if (stack.getItem() instanceof ItemExcalibur && entityLiving instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer)entityLiving;
				if (player.getHeldItem(EnumHand.MAIN_HAND).getItemDamage() == 1) {
					excalibur(player);
					this.chargeTicks = 0;
					player.getHeldItem(EnumHand.MAIN_HAND).setItemDamage(0);
				}
			}
			return super.onEntitySwing(entityLiving, stack);
		}
		return true;
	}

	/***
	 * The main part of Excalibur
	 * @param player
	 */
	private void excalibur(EntityPlayer player) {
		World world = player.worldObj;
		Vec3d playerEyePos = new Vec3d(player.posX, player.posY+1.5, player.posZ);
		Vec3d gaze = player.getLookVec();
		Vec3d vecH = new Vec3d(gaze.xCoord, 0, gaze.zCoord).normalize().scale(ATTACKRANGE+5);
		for (int angle = 90;angle>=0;angle-=6) {
			Vec3d direction = MathHelper.getPointByAngle(angle, vecH.xCoord, 32, vecH.zCoord).normalize();
			for (int i = 5;i<=ATTACKRANGE;i+=3) {
				Vec3d p = playerEyePos.add(direction.scale(i));
				affectedBlockPositions.add(new BlockPos(p));
			}
		}
		
		for (BlockPos pos:affectedBlockPositions) {
			new ExplosionUnharmful(world, 3.0F, pos.getX(), pos.getY(), pos.getZ()).explode();
			
			affectedEntityList.addAll(world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(new BlockPos(pos.getX()-3, pos.getY()-3, pos.getZ()-3), new BlockPos(pos.getX()+4, pos.getY()+4, pos.getZ()+4))));
		}
		
		for (Entity entity:affectedEntityList)
			entity.attackEntityFrom(ModDamageSources.dmgsrcExcalibur, 20.0F);
		
			
		affectedBlockPositions.clear();
		affectedEntityList.clear();
	}
	
}