package hmysjiang.usefulstuffs.proxy;

import hmysjiang.usefulstuffs.init.ModEntities;
import hmysjiang.usefulstuffs.utils.KeyBindingHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit() {
		super.preInit();
		ModEntities.registerRenders();
	}
	
	@Override
	public void init() {
		super.init();
		KeyBindingHandler.init();
	}
	
	@Override
	public void registerItemRenders(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
}
