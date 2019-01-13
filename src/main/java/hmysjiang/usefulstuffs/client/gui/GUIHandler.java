package hmysjiang.usefulstuffs.client.gui;

import baubles.api.cap.BaublesCapabilities;
import hmysjiang.usefulstuffs.blocks.universaluser.TileEntityUniversalUser;
import hmysjiang.usefulstuffs.container.ContainerBackpack;
import hmysjiang.usefulstuffs.container.ContainerBento;
import hmysjiang.usefulstuffs.container.ContainerCampfire;
import hmysjiang.usefulstuffs.container.ContainerFilingCabinetNBT;
import hmysjiang.usefulstuffs.container.ContainerFilingCabinetUnstackable;
import hmysjiang.usefulstuffs.container.ContainerLightShooter;
import hmysjiang.usefulstuffs.container.ContainerMilkFermenter;
import hmysjiang.usefulstuffs.container.ContainerMiningBackpackConfig;
import hmysjiang.usefulstuffs.container.ContainerUniversalUser;
import hmysjiang.usefulstuffs.items.ItemBento;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int GUI_FILING_CABINET_1 = 0;
	public static final int GUI_FILING_CABINET_2 = 1;
	public static final int GUI_FILING_CABINET_3 = 2;
	public static final int GUI_FILING_CABINET_4 = 3;
	public static final int GUI_FILING_CABINET_5 = 4;
	public static final int GUI_FILING_CABINET_6 = 5;
	public static final int GUI_FILING_CABINET_7 = 6;
	public static final int GUI_FILING_CABINET_8 = 7;
	public static final int GUI_FILING_CABINET_9 = 8;
	public static final int GUI_FILING_CABINET_10 = 9;
	public static final int GUI_BENTO = 10;
	public static final int GUI_LIGHT_SHOOTER = 11;
	public static final int GUI_CAMPFIRE = 12;
	public static final int GUI_BACKPACK = 13;
	public static final int GUI_BACKPACK_BAUBLE = 14;
	public static final int GUI_BACKPACK_MINING_CONFIG = 15;
	public static final int GUI_FERMENTER = 16;
	public static final int GUI_USER = 17;
	public static final int GUI_FILING_CABINET2_1 = 18;
	public static final int GUI_FILING_CABINET2_2 = 19;
	public static final int GUI_FILING_CABINET2_3 = 20;
	public static final int GUI_FILING_CABINET2_4 = 21;
	public static final int GUI_FILING_CABINET2_5 = 22;
	public static final int GUI_FILING_CABINET2_6 = 23;
	public static final int GUI_FILING_CABINET2_7 = 24;
	public static final int GUI_FILING_CABINET2_8 = 25;
	public static final int GUI_FILING_CABINET2_9 = 26;
	public static final int GUI_FILING_CABINET2_10 = 27;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
		case GUI_FILING_CABINET_1:
			return new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 0);
		case GUI_FILING_CABINET_2:
			return new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 1);
		case GUI_FILING_CABINET_3:
			return new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 2);
		case GUI_FILING_CABINET_4:
			return new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 3);
		case GUI_FILING_CABINET_5:
			return new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 4);
		case GUI_FILING_CABINET_6:
			return new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 5);
		case GUI_FILING_CABINET_7:
			return new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 6);
		case GUI_FILING_CABINET_8:
			return new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 7);
		case GUI_FILING_CABINET_9:
			return new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 8);
		case GUI_FILING_CABINET_10:
			return new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 9);
		case GUI_BENTO:
			if (player.getHeldItemMainhand().getItem() instanceof ItemBento)
				return new ContainerBento(player.inventory, player.getHeldItemMainhand(), 6);
		case GUI_LIGHT_SHOOTER:
			return new ContainerLightShooter(player.inventory, player.getHeldItemMainhand(), 4);
		case GUI_CAMPFIRE:
			return new ContainerCampfire(player, new BlockPos(x, y, z));
		case GUI_BACKPACK:
			return new ContainerBackpack(player.inventory, player.getHeldItemMainhand(), 54, false);
		case GUI_BACKPACK_BAUBLE:
			return new ContainerBackpack(player.inventory, player.getCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null).getStackInSlot(5), 54, true);
		case GUI_BACKPACK_MINING_CONFIG:
			return new ContainerMiningBackpackConfig(player, player.getHeldItemMainhand());
		case GUI_FERMENTER:
			return new ContainerMilkFermenter(player, new BlockPos(x, y, z));
		case GUI_USER:
			return new ContainerUniversalUser(player.inventory, (TileEntityUniversalUser) world.getTileEntity(new BlockPos(x, y, z)));
		case GUI_FILING_CABINET2_1:
			return new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 0);
		case GUI_FILING_CABINET2_2:
			return new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 1);
		case GUI_FILING_CABINET2_3:
			return new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 2);
		case GUI_FILING_CABINET2_4:
			return new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 3);
		case GUI_FILING_CABINET2_5:
			return new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 4);
		case GUI_FILING_CABINET2_6:
			return new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 5);
		case GUI_FILING_CABINET2_7:
			return new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 6);
		case GUI_FILING_CABINET2_8:
			return new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 7);
		case GUI_FILING_CABINET2_9:
			return new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 8);
		case GUI_FILING_CABINET2_10:
			return new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 9);
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
		case GUI_FILING_CABINET_1:
			return new GuiFilingCabinetUnstackable(new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 0));
		case GUI_FILING_CABINET_2:
			return new GuiFilingCabinetUnstackable(new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 1));
		case GUI_FILING_CABINET_3:
			return new GuiFilingCabinetUnstackable(new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 2));
		case GUI_FILING_CABINET_4:
			return new GuiFilingCabinetUnstackable(new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 3));
		case GUI_FILING_CABINET_5:
			return new GuiFilingCabinetUnstackable(new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 4));
		case GUI_FILING_CABINET_6:
			return new GuiFilingCabinetUnstackable(new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 5));
		case GUI_FILING_CABINET_7:
			return new GuiFilingCabinetUnstackable(new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 6));
		case GUI_FILING_CABINET_8:
			return new GuiFilingCabinetUnstackable(new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 7));
		case GUI_FILING_CABINET_9:
			return new GuiFilingCabinetUnstackable(new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 8));
		case GUI_FILING_CABINET_10:
			return new GuiFilingCabinetUnstackable(new ContainerFilingCabinetUnstackable(player, new BlockPos(x, y, z), 9));
		case GUI_BENTO:
			return new GuiBento(new ContainerBento(player.inventory, player.getHeldItemMainhand(), 6));
		case GUI_LIGHT_SHOOTER:
			return new GuiLightShooter(new ContainerLightShooter(player.inventory, player.getHeldItemMainhand(), 4));
		case GUI_CAMPFIRE:
			return new GuiCampfire(new ContainerCampfire(player, new BlockPos(x, y, z)));
		case GUI_BACKPACK:
			return new GuiBackpack(new ContainerBackpack(player.inventory, player.getHeldItemMainhand(), 54, false));
		case GUI_BACKPACK_BAUBLE:
			return new GuiBackpack(new ContainerBackpack(player.inventory, player.getCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null).getStackInSlot(5), 54, true));
		case GUI_BACKPACK_MINING_CONFIG:
			return new GuiMiningBackpackConfig(new ContainerMiningBackpackConfig(player, player.getHeldItemMainhand()));
		case GUI_FERMENTER:
			return new GuiMilkFermenter(new ContainerMilkFermenter(player, new BlockPos(x, y, z)));
		case GUI_USER:
			return new GuiUniversalUser(new ContainerUniversalUser(player.inventory, (TileEntityUniversalUser) world.getTileEntity(new BlockPos(x, y, z))));
		case GUI_FILING_CABINET2_1:
			return new GuiFilingCabinetNBT(new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 0));
		case GUI_FILING_CABINET2_2:
			return new GuiFilingCabinetNBT(new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 1));
		case GUI_FILING_CABINET2_3:
			return new GuiFilingCabinetNBT(new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 2));
		case GUI_FILING_CABINET2_4:
			return new GuiFilingCabinetNBT(new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 3));
		case GUI_FILING_CABINET2_5:
			return new GuiFilingCabinetNBT(new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 4));
		case GUI_FILING_CABINET2_6:
			return new GuiFilingCabinetNBT(new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 5));
		case GUI_FILING_CABINET2_7:
			return new GuiFilingCabinetNBT(new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 6));
		case GUI_FILING_CABINET2_8:
			return new GuiFilingCabinetNBT(new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 7));
		case GUI_FILING_CABINET2_9:
			return new GuiFilingCabinetNBT(new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 8));
		case GUI_FILING_CABINET2_10:
			return new GuiFilingCabinetNBT(new ContainerFilingCabinetNBT(player, new BlockPos(x, y, z), 9));
		default:
			return null;
		}
	}

}
