package hmysjiang.usefulstuffs.client.gui;

import baubles.api.cap.BaublesCapabilities;
import hmysjiang.usefulstuffs.blocks.campfire.TileEntityCampfire;
import hmysjiang.usefulstuffs.container.ContainerBento;
import hmysjiang.usefulstuffs.container.ContainerCampfire;
import hmysjiang.usefulstuffs.container.ContainerFilingCabinet;
import hmysjiang.usefulstuffs.container.ContainerBackpack;
import hmysjiang.usefulstuffs.container.ContainerLightShooter;
import hmysjiang.usefulstuffs.container.ContainerMilkFermenter;
import hmysjiang.usefulstuffs.container.ContainerMiningBackpackConfig;
import hmysjiang.usefulstuffs.items.ItemBento;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

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

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
		case GUI_FILING_CABINET_1:
			return new ContainerFilingCabinet(player, new BlockPos(x, y, z), 0);
		case GUI_FILING_CABINET_2:
			return new ContainerFilingCabinet(player, new BlockPos(x, y, z), 1);
		case GUI_FILING_CABINET_3:
			return new ContainerFilingCabinet(player, new BlockPos(x, y, z), 2);
		case GUI_FILING_CABINET_4:
			return new ContainerFilingCabinet(player, new BlockPos(x, y, z), 3);
		case GUI_FILING_CABINET_5:
			return new ContainerFilingCabinet(player, new BlockPos(x, y, z), 4);
		case GUI_FILING_CABINET_6:
			return new ContainerFilingCabinet(player, new BlockPos(x, y, z), 5);
		case GUI_FILING_CABINET_7:
			return new ContainerFilingCabinet(player, new BlockPos(x, y, z), 6);
		case GUI_FILING_CABINET_8:
			return new ContainerFilingCabinet(player, new BlockPos(x, y, z), 7);
		case GUI_FILING_CABINET_9:
			return new ContainerFilingCabinet(player, new BlockPos(x, y, z), 8);
		case GUI_FILING_CABINET_10:
			return new ContainerFilingCabinet(player, new BlockPos(x, y, z), 9);
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
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
		case GUI_FILING_CABINET_1:
			return new GuiFilingCabinet(new ContainerFilingCabinet(player, new BlockPos(x, y, z), 0));
		case GUI_FILING_CABINET_2:
			return new GuiFilingCabinet(new ContainerFilingCabinet(player, new BlockPos(x, y, z), 1));
		case GUI_FILING_CABINET_3:
			return new GuiFilingCabinet(new ContainerFilingCabinet(player, new BlockPos(x, y, z), 2));
		case GUI_FILING_CABINET_4:
			return new GuiFilingCabinet(new ContainerFilingCabinet(player, new BlockPos(x, y, z), 3));
		case GUI_FILING_CABINET_5:
			return new GuiFilingCabinet(new ContainerFilingCabinet(player, new BlockPos(x, y, z), 4));
		case GUI_FILING_CABINET_6:
			return new GuiFilingCabinet(new ContainerFilingCabinet(player, new BlockPos(x, y, z), 5));
		case GUI_FILING_CABINET_7:
			return new GuiFilingCabinet(new ContainerFilingCabinet(player, new BlockPos(x, y, z), 6));
		case GUI_FILING_CABINET_8:
			return new GuiFilingCabinet(new ContainerFilingCabinet(player, new BlockPos(x, y, z), 7));
		case GUI_FILING_CABINET_9:
			return new GuiFilingCabinet(new ContainerFilingCabinet(player, new BlockPos(x, y, z), 8));
		case GUI_FILING_CABINET_10:
			return new GuiFilingCabinet(new ContainerFilingCabinet(player, new BlockPos(x, y, z), 9));
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
			return new GuiMIlkFermenter(new ContainerMilkFermenter(player, new BlockPos(x, y, z)));
		default:
			return null;
		}
	}

}
