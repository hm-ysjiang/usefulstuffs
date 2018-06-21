package hmysjiang.usefulstuffs.client.gui;

import hmysjiang.usefulstuffs.container.ContainerBento;
import hmysjiang.usefulstuffs.container.ContainerGunInventory;
import hmysjiang.usefulstuffs.items.ItemBento;
import hmysjiang.usefulstuffs.items.gun.ItemGunBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {
	
	public static final int GUI_GUN_INV = 0;
	public static final int GUI_BENTO = 1;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
		case GUI_GUN_INV:
			if (player.getHeldItemMainhand().getItem() instanceof ItemGunBase)
				return new ContainerGunInventory(player.inventory, player.getHeldItemMainhand(), 4);
		case GUI_BENTO:
			if (player.getHeldItemMainhand().getItem() instanceof ItemBento)
				return new ContainerBento(player.inventory, player.getHeldItemMainhand(), 6);
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
		case GUI_GUN_INV:
			if (player.getHeldItemMainhand().getItem() instanceof ItemGunBase)
				return new GuiGunInventory(new ContainerGunInventory(player.inventory, player.getHeldItemMainhand(), 4));
		case GUI_BENTO:
			if (player.getHeldItemMainhand().getItem() instanceof ItemBento)
				return new GuiBento(new ContainerBento(player.inventory, player.getHeldItemMainhand(), 6));
		default:
			return null;
		}
	}

}
