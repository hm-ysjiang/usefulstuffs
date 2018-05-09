package hmysjiang.usefulstuffs.proxy;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.init.*;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.util.ResourceLocation;

public class ClientProxy implements CommonProxy {

	@Override
	public void registerRenders() {
		ModItems.registerRenders();
		ModBlocks.registerRenders();
		ModEntities.registerRenders();
	}

	@Override
	public void registerModelBakeryVariants() {
		ModelBakery.registerItemVariants(ModItems.excalibur, new ResourceLocation(Reference.MOD_ID, "excalibur_uncharged"), new ResourceLocation(Reference.MOD_ID, "excalibur_charged"));
	}

}
