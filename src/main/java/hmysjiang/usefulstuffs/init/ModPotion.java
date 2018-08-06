package hmysjiang.usefulstuffs.init;

import hmysjiang.usefulstuffs.potion.PotionLily;
import hmysjiang.usefulstuffs.potion.potiontype.PotionTypeLily;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ModPotion {
	
	@SubscribeEvent
	public static void onPotionRegister(RegistryEvent.Register<Potion> event) {
		event.getRegistry().register(PotionLily.instance);
	}
	
	@SubscribeEvent
	public static void onPotionTypeRegister(RegistryEvent.Register<PotionType> event) {
		event.getRegistry().register(PotionTypeLily.instance);
		event.getRegistry().register(PotionTypeLily.instance_long);
	}
	
	public static void registerBrewingRecipe() {
		BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), new ItemStack(Blocks.WATERLILY), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeLily.instance)));
		BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeLily.instance), new ItemStack(Items.REDSTONE), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeLily.instance_long)));
		BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeLily.instance), new ItemStack(Items.GUNPOWDER), PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypeLily.instance)));
		BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeLily.instance_long), new ItemStack(Items.GUNPOWDER), PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypeLily.instance_long)));
		BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeLily.instance), new ItemStack(Items.DRAGON_BREATH), PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypeLily.instance)));
		BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeLily.instance_long), new ItemStack(Items.DRAGON_BREATH), PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypeLily.instance_long)));
	}
	
}
