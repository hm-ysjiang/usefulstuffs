package hmysjiang.usefulstuffs.proxy;

import hmysjiang.usefulstuffs.ConfigManager;
import hmysjiang.usefulstuffs.blocks.bush.EnumBerryColor;
import hmysjiang.usefulstuffs.blocks.portalmuffler.PortalMufflerManager;
import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModEntities;
import hmysjiang.usefulstuffs.init.ModItems;
import hmysjiang.usefulstuffs.utils.handler.KeyBindingHandler;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit() {
		super.preInit();
		ModBlocks.createStateMapper();
		ModEntities.registerRenders();
		
		KeyBindingHandler.init();
		
		if (ConfigManager.portalMufflerEnabled)
			MinecraftForge.EVENT_BUS.register(PortalMufflerManager.INSTANCE);
	}
	
	@Override
	public void registerItemRenders(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	@Override
	public void registerItemRenders(Item item, int meta, String name) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName().toString() + "_" + name, "inventory"));
	}
	
	@Override
	public void registerItemVariants() {
		ResourceLocation[] berry_rsrc = new ResourceLocation[16];
		for (int i = 0 ; i<EnumDyeColor.values().length ; i++) 
			berry_rsrc[i] = new ResourceLocation(ModItems.berry.getRegistryName() + "_" + EnumBerryColor.byMetadata(i).getDyeColorName());
		ModelBakery.registerItemVariants(ModItems.berry, berry_rsrc);
	}
	
}
