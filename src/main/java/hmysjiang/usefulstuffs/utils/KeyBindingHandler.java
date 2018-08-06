package hmysjiang.usefulstuffs.utils;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import hmysjiang.usefulstuffs.network.PacketHandler;
import hmysjiang.usefulstuffs.network.packet.KeyInput;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@EventBusSubscriber
public class KeyBindingHandler {

	public static KeyBindingHandler instance = new KeyBindingHandler();
	
	public static List<KeyBinding> keybindings;
	
	public static void init() {
		keybindings = new ArrayList<KeyBinding>();
		keybindings.add(new KeyBinding("key.usefulstuffs.switchinv.desc", Keyboard.KEY_X, "key.usefulstuffs.category"));
		
		registerKeyBindings();
	}
	
	public static void registerKeyBindings() {
		for (KeyBinding binding:keybindings) {
			ClientRegistry.registerKeyBinding(binding);
		}
	}
	
	@SubscribeEvent
	public static void onKeyInput(KeyInputEvent event) {
		if (keybindings.get(0).isPressed()) {
			PacketHandler.INSTANCE.sendToServer(new KeyInput(0));
		}
	}
	
}
