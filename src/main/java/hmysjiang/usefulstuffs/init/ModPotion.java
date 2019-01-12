package hmysjiang.usefulstuffs.init;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.potion.*;
import hmysjiang.usefulstuffs.potion.potiontype.*;
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
		if (ConfigManager.potionLilyEnabled)
			event.getRegistry().register(PotionLily.instance);
		if (ConfigManager.potionFieryLilyEnabled)
			event.getRegistry().register(PotionFieryLily.instance);
		if (ConfigManager.potionParachuteEnabled)
			event.getRegistry().register(PotionUmbrella.instance);
	}
	
	@SubscribeEvent
	public static void onPotionTypeRegister(RegistryEvent.Register<PotionType> event) {
		if (ConfigManager.potionLilyEnabled) {
			event.getRegistry().register(PotionTypeLily.instance);
			event.getRegistry().register(PotionTypeLily.instance_long);
		}
		if (ConfigManager.potionFieryLilyEnabled) {
			event.getRegistry().register(PotionTypeFieryLily.instance);
			event.getRegistry().register(PotionTypeFieryLily.instance_long);
		}
		if (ConfigManager.potionParachuteEnabled) {
			event.getRegistry().register(PotionTypeUmbrella.instance);
			event.getRegistry().register(PotionTypeUmbrella.instance_long);
			event.getRegistry().register(PotionTypeUmbrella.instance_strong);
		}
	}
	
	public static void registerBrewingRecipe() {
		if (ConfigManager.potionLilyEnabled) {
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), new ItemStack(Blocks.WATERLILY), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeLily.instance)));
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeLily.instance), new ItemStack(Items.REDSTONE), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeLily.instance_long)));
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeLily.instance), new ItemStack(Items.GUNPOWDER), PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypeLily.instance)));
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeLily.instance_long), new ItemStack(Items.GUNPOWDER), PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypeLily.instance_long)));
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeLily.instance), new ItemStack(Items.DRAGON_BREATH), PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypeLily.instance)));
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeLily.instance_long), new ItemStack(Items.DRAGON_BREATH), PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypeLily.instance_long)));	
		}
		
		if (ConfigManager.potionFieryLilyEnabled) {
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), new ItemStack(ModBlocks.fiery_lily), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeFieryLily.instance)));
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeFieryLily.instance), new ItemStack(Items.REDSTONE), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeFieryLily.instance_long)));
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeFieryLily.instance), new ItemStack(Items.GUNPOWDER), PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypeFieryLily.instance)));
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeFieryLily.instance_long), new ItemStack(Items.GUNPOWDER), PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypeFieryLily.instance_long)));
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeFieryLily.instance), new ItemStack(Items.DRAGON_BREATH), PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypeFieryLily.instance)));
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeFieryLily.instance_long), new ItemStack(Items.DRAGON_BREATH), PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypeFieryLily.instance_long)));
		}
		
		if (ConfigManager.potionParachuteEnabled) {
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD), new ItemStack(ModItems.umbrella), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeUmbrella.instance)));
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeUmbrella.instance), new ItemStack(Items.REDSTONE), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeUmbrella.instance_long)));
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeUmbrella.instance), new ItemStack(Items.GLOWSTONE_DUST), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeUmbrella.instance_strong)));
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeUmbrella.instance), new ItemStack(Items.GUNPOWDER), PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypeUmbrella.instance)));
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeUmbrella.instance_long), new ItemStack(Items.GUNPOWDER), PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypeUmbrella.instance_long)));
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeUmbrella.instance_strong), new ItemStack(Items.GUNPOWDER), PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypeUmbrella.instance_strong)));
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeUmbrella.instance), new ItemStack(Items.DRAGON_BREATH), PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypeUmbrella.instance)));
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeUmbrella.instance_long), new ItemStack(Items.DRAGON_BREATH), PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypeUmbrella.instance_long)));
			BrewingRecipeRegistry.addRecipe(new BrewingRecipe(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypeUmbrella.instance_strong), new ItemStack(Items.DRAGON_BREATH), PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypeUmbrella.instance_strong)));
		}
	}
	
}
