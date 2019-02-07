package hmysjiang.usefulstuffs.network;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.network.packet.BackpackPropertyUpdate;
import hmysjiang.usefulstuffs.network.packet.BentoUpdateMode;
import hmysjiang.usefulstuffs.network.packet.FieryRemoved;
import hmysjiang.usefulstuffs.network.packet.GuiButtonPressed;
import hmysjiang.usefulstuffs.network.packet.GuiSortPressed;
import hmysjiang.usefulstuffs.network.packet.KeyInput;
import hmysjiang.usefulstuffs.network.packet.PlaySound;
import hmysjiang.usefulstuffs.network.packet.SyncLightArrow;
import hmysjiang.usefulstuffs.network.packet.UserPropertyUpdate;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

	public static SimpleNetworkWrapper INSTANCE;
	
	public static void init() {
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
		
		int disc = 0;
		INSTANCE.registerMessage(GuiButtonPressed.Handler.class, GuiButtonPressed.class, disc++, Side.SERVER);
		INSTANCE.registerMessage(GuiSortPressed.Handler.class, GuiSortPressed.class, disc++, Side.SERVER);
		INSTANCE.registerMessage(KeyInput.Handler.class, KeyInput.class, disc++, Side.SERVER);
		INSTANCE.registerMessage(BackpackPropertyUpdate.Handler.class, BackpackPropertyUpdate.class, disc++, Side.SERVER);
		INSTANCE.registerMessage(UserPropertyUpdate.Handler.class, UserPropertyUpdate.class, disc++, Side.SERVER);
		INSTANCE.registerMessage(BentoUpdateMode.Handler.class, BentoUpdateMode.class, disc++, Side.SERVER);
		
		INSTANCE.registerMessage(FieryRemoved.Handler.class, FieryRemoved.class, disc++, Side.CLIENT);
		INSTANCE.registerMessage(SyncLightArrow.Handler.class, SyncLightArrow.class, disc++, Side.CLIENT);
		INSTANCE.registerMessage(PlaySound.Handler.class, PlaySound.class, disc++, Side.CLIENT);
	}
	
}
