package hmysjiang.usefulstuffs.proxy;

import hmysjiang.usefulstuffs.event.SpriteHandler;
import hmysjiang.usefulstuffs.init.ModBlocks;
import hmysjiang.usefulstuffs.init.ModEntities;
import hmysjiang.usefulstuffs.init.ModItems;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenders() {
		ModItems.registerRenders();
		ModBlocks.registerRenders();
		ModEntities.registerRenders();
	}

	@Override
	public void registerModelBakeryVariants() {
//		ModelBakery.registerItemVariants(ModItems.bullet, new ResourceLocation(Reference.MOD_ID, "bullet_shell"), new ResourceLocation(Reference.MOD_ID, "bullet_normal"));
	}
	
	@Override
	public void registerSideOnlyEvents() {
		MinecraftForge.EVENT_BUS.register(SpriteHandler.instance);
		super.registerSideOnlyEvents();
	}

}