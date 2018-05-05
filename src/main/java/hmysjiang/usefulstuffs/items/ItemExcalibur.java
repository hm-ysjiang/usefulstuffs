package hmysjiang.usefulstuffs.items;

import java.util.List;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.handlers.AchievementHandler;
import hmysjiang.usefulstuffs.handlers.EnumHandler.ExcaliburType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class ItemExcalibur extends ItemSword {
	
	private static final int HARVESTLEVEL = 0;
	private static final int MAXUSES = 1628;
	private static final float EFFICIENCY = 0F;
	private static final float DAMAGE = 7.5F;
	private static final int ENCHANTABILITY = 14;
	private static final int FULLCHARGETICK = 50;
	
	private int chargeTicks;
	

	public ItemExcalibur() {
		super(EnumHelper.addToolMaterial(Reference.MOD_ID + "excalibur", HARVESTLEVEL, MAXUSES, EFFICIENCY, DAMAGE, ENCHANTABILITY));
		setUnlocalizedName(Reference.ModItems.EXCALIBUR.getUnlocalizedName());
		setRegistryName(Reference.ModItems.EXCALIBUR.getRegistryName());
		this.setHasSubtypes(true);
		setMaxDamage(0);
		
		this.chargeTicks = 0;
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
		super.addInformation(stack, playerIn, tooltip, advanced);
		tooltip.add("excalibur.tooltip");
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
			if (stack.getItem() instanceof ItemExcalibur) {
				if (entityLiving instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer)entityLiving;
					if (this.chargeTicks == FULLCHARGETICK && player.experienceTotal >= 40) {
						System.out.println(String.format("%s swang The Excalibur!", player.getName()));
						this.chargeTicks = 0;
						player.experienceTotal -=40;
						player.getHeldItem(EnumHand.MAIN_HAND).setItemDamage(0);
					}
					return super.onEntitySwing(entityLiving, stack);
				}	
			}
			return super.onEntitySwing(entityLiving, stack);
		}
		return true;
	}
	
}
