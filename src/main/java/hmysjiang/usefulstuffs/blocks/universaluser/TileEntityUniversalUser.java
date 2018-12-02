package hmysjiang.usefulstuffs.blocks.universaluser;

import java.lang.ref.WeakReference;
import java.util.Random;

import hmysjiang.usefulstuffs.network.PacketHandler;
import hmysjiang.usefulstuffs.network.packet.SyncUser;
import hmysjiang.usefulstuffs.utils.fakeplayer.FakePlayerHandler;
import hmysjiang.usefulstuffs.utils.helper.LogHelper;
import hmysjiang.usefulstuffs.utils.helper.WorldHelper;
import net.minecraft.block.BlockLever;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityUniversalUser extends TileEntity implements ITickable {
	
	private static Random rnd = new Random();
	
	private ItemStackHandler inventory;
	private FluidTank tank;
	//The Energy that machine consume
	private EnergyBank capacitor;
	//The Energy that machine uses to charge item
	private EnergyBank innerCapacitor;
	private int workTime;
	public OperateSpeed operateSpeed;
	public Activation activation;
	public Button button;
	public Select select;
	public Redstone redstone;
	
	public TileEntityUniversalUser() {
		this.inventory = new ItemStackHandler(9);
		this.tank = new FluidTank(4000);
		this.capacitor = new EnergyBank(1000000);
		this.innerCapacitor = new EnergyBank(500000, 5000, 10000);
		workTime = 0;
		operateSpeed = OperateSpeed.SLOW;
		activation = Activation.ACTIVATE_BLOCK;
		button = Button.RIGHT;
		select = Select.UPPER_LEFT;
		redstone = Redstone.ACTIVE_ON_REDSTONE;
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			PacketHandler.INSTANCE.sendToDimension(new SyncUser(pos.getX(), pos.getY(), pos.getZ(), writeToNBT(new NBTTagCompound())), world.provider.getDimension());
			if (workTime == operateSpeed.cooldown) {
				//Consume Energy
				if (capacitor.getEnergyStored() < operateSpeed.getEnergyCost()) 
					return;
				capacitor.extractEnergy(operateSpeed.getEnergyCost(), false);
				//Do work
				WeakReference<FakePlayer> refPlayer = FakePlayerHandler.INSTANCE.getFakePlayer((WorldServer) world);
				refPlayer.get().posX = (double) pos.getX() + 0.5;
				refPlayer.get().posY = (double) pos.getY() + 0.5;
				refPlayer.get().posZ = (double) pos.getZ() + 0.5;
				for (int i = 0 ; i<9 ; i++) {
					refPlayer.get().inventory.setInventorySlotContents(i, inventory.getStackInSlot(i));
				}
				refPlayer.get().inventory.currentItem = (select == Select.RANDOM ? getRandom() : 0);
				EnumFacing facing = world.getBlockState(pos).getValue(BlockUniversalUser.FACING);
				IBlockState state = world.getBlockState(pos.offset(facing));
				ItemStack stack = refPlayer.get().inventory.getStackInSlot(refPlayer.get().inventory.currentItem);
				refPlayer.get().setSneaking(button.isSneakMode());
				switch (activation) {
				case ACTIVATE_BLOCK:
					if (button == Button.RIGHT) {
						LogHelper.info("" + refPlayer.get().connection);
//						RightClickBlock event = ForgeHooks.onRightClickBlock(refPlayer.get(), EnumHand.MAIN_HAND, pos.offset(facing), facing.getOpposite(), WorldHelper.getHitVecFromAdjacent(facing));
//						if (!event.isCanceled() && event.getUseBlock() != Result.DENY)
//							state.getBlock().onBlockActivated(world, pos.offset(facing), state, refPlayer.get(), EnumHand.MAIN_HAND, facing.getOpposite(), 0.5F, 0.5F, 0.5F);
					}
					break;
				case CLICK_BLOCK:
					PlayerInteractEvent event = null;
					switch (button) {
					case LEFT:
						event = ForgeHooks.onLeftClickBlock(refPlayer.get(), pos.offset(facing), facing.getOpposite(), WorldHelper.getHitVecFromAdjacent(facing));
						break;
					case RIGHT:
						event = ForgeHooks.onRightClickBlock(refPlayer.get(), EnumHand.MAIN_HAND, pos.offset(facing), facing.getOpposite(), WorldHelper.getHitVecFromAdjacent(facing));
						break;
					default:
						break;
					}
					if (event != null && !event.isCanceled()) {
						if (event instanceof LeftClickBlock && ((LeftClickBlock) event).getUseBlock() != Result.DENY)
							state.getBlock().onBlockClicked(world, pos.offset(facing), refPlayer.get());
						else if (event instanceof RightClickBlock)
							state.getBlock().onBlockClicked(world, pos.offset(facing), refPlayer.get());
					}
//					state.getBlock().onBlockClicked(world, pos.offset(facing), refPlayer.get());
					break;
				case CLICK_ITEM:
					switch (button) {
					case LEFT:
						EntityLivingBase living = WorldHelper.rayTraceEntity(world, pos, facing, 1.5);
						if (living != null && !stack.getItem().onLeftClickEntity(stack, refPlayer.get(), living)) {
							refPlayer.get().ticksSinceLastSwing = 32767;
							refPlayer.get().attackTargetEntityWithCurrentItem(living);
						}
						break;
					case RIGHT:
						stack.getItem().onItemRightClick(world, refPlayer.get(), EnumHand.MAIN_HAND);
						break;
					default:
						break;
					}
					break;
				case ENTITY:
					EntityLivingBase living = WorldHelper.rayTraceEntity(world, pos, facing, 1.5);
					if (living != null) {
						switch (button) {
						case LEFT:
							refPlayer.get().ticksSinceLastSwing = 32767;
							LogHelper.info("f: " + (float)refPlayer.get().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
							LogHelper.info("f3: " + 1.0F + EnchantmentHelper.getSweepingDamageRatio(refPlayer.get()) * (float)refPlayer.get().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
							refPlayer.get().attackTargetEntityWithCurrentItem(living);
							break;
						case RIGHT:
							stack.getItem().itemInteractionForEntity(stack, refPlayer.get(), living, EnumHand.MAIN_HAND);
							break;
						default:
							break;
						}
					}
					break;
				case USE_ITEM:
					if (button == Button.RIGHT) {
						stack.onItemUse(refPlayer.get(), world, pos.offset(facing), EnumHand.MAIN_HAND, facing.getOpposite(), 0.5F, 0.5F, 0.5F);
					}
					break;
				default:
					break;
				}
				refPlayer.get().setSneaking(false);
				resetWorkTime();
				for (int i = 0 ; i<9 ; i++) {
					if (inventory.getStackInSlot(i).isEmpty() && !refPlayer.get().inventory.getStackInSlot(i).isEmpty())
						inventory.setStackInSlot(i, refPlayer.get().inventory.getStackInSlot(i));
				}
				this.markDirty();
			}
			else {
				workTime++;
			}
			if (innerCapacitor.getEnergyStored() < innerCapacitor.getMaxEnergyStored()) {
				//Transfer energy
				int charge = innerCapacitor.receiveEnergy(capacitor.extractEnergy(5000, true), true);
				innerCapacitor.receiveEnergy(capacitor.extractEnergy(charge, false), false);
				this.markDirty();
			}
			for (int i = 0 ; i<inventory.getSlots() ; i++) {
				ItemStack stack = inventory.getStackInSlot(i);
				if (stack.isEmpty())
					continue;
				if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
					IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
					//Do charge
					int charge = storage.receiveEnergy(innerCapacitor.extractEnergy(10000, true), true);
					storage.receiveEnergy(innerCapacitor.extractEnergy(charge, false), false);
					this.markDirty();
				}
				if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
					IFluidHandlerItem handler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
					if (tank.getFluid() != null && handler.fill(tank.getFluid(), false) > 0) {
						//Do refill
						FluidStack fluid = tank.drain(250, true);
						fluid.amount -= handler.fill(fluid.copy(), true);
						tank.fill(fluid, true);
						this.markDirty();
					}
				}
			}
		}
	}
	
	private int getRandom() {
		int flag = 0, ret;
		for (int i = 0 ; i<9 ; i++)
			if (!inventory.getStackInSlot(i).isEmpty())
				flag += 1 << i;
		if (flag == 0)	return 0;
		do {
			ret = rnd.nextInt(9);
		} while((flag & (1 << ret)) == 0);
		return ret;
	}
	
	public void resetWorkTime() {
		this.workTime = 0;
	}
	
	public EnergyBank getInnerCapacitor() {
		return innerCapacitor;
	}
	
	public EnergyBank getCapacitor() {
		return capacitor;
	}
	
	public FluidTank getTank() {
		return tank;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return this.getCapability(capability, facing) != null; 
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (facing == world.getBlockState(pos).getValue(BlockUniversalUser.FACING))
			return null;
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) inventory;
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return (T) tank;
		if (capability == CapabilityEnergy.ENERGY)
			return (T) capacitor;
		return null;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		tank.readFromNBT(compound.getCompoundTag("tank"));
		capacitor.setEnergy(compound.getInteger("energy"));
		innerCapacitor.setEnergy(compound.getInteger("innerEnergy"));
		workTime = compound.getInteger("workTime");
		operateSpeed = OperateSpeed.fromId(compound.getInteger("operateSpeed"));
		activation = Activation.fromId(compound.getInteger("activation"));
		button = Button.fromId(compound.getInteger("button"));
		select = Select.fromId(compound.getInteger("select"));
		redstone = Redstone.fromId(compound.getInteger("redstone"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		compound.setInteger("energy", capacitor.getEnergyStored());
		compound.setInteger("innerEnergy", innerCapacitor.getEnergyStored());
		compound.setInteger("workTime", workTime);
		compound.setInteger("operateSpeed", operateSpeed.getId());
		compound.setInteger("activation", activation.getId());
		compound.setInteger("button", button.getId());
		compound.setInteger("select", select.getId());
		compound.setInteger("redstone", redstone.getId());
		return super.writeToNBT(compound);
	}
	
	public static enum OperateSpeed {
		SLOW(0, 25, 200),
		FAST(1, 10, 275),
		FASTER(2, 5, 325),
		FASTEST(3, 1, 400);
		
		private static final OperateSpeed[] LOOK_UP = new OperateSpeed[5];
		private int id;
		private int cooldown;
		private int energyCost;
		
		private OperateSpeed(int id, int cooldown, int energyCost) {
			this.id = id;
			this.cooldown = cooldown;
			this.energyCost = energyCost;
		}
		
		public int getId() {
			return this.id;
		}
		
		public int getCooldown() {
			return this.cooldown;
		}
		
		public int getEnergyCost() {
			return this.energyCost;
		}
		
		public OperateSpeed next() {
			return fromId(this.getId() + 1);
		}
		
		@Override
		public String toString() {
			switch (this) {
			case FAST:
				return "Fast";
			case FASTER:
				return "Faster";
			case FASTEST:
				return "Fastest";
			case SLOW:
				return "Slow";
			default:
				return "";
			}
		}
		
		public static OperateSpeed fromId(int id) {
			return LOOK_UP[id];
		}
		
		static {
			for (OperateSpeed item: values())
				LOOK_UP[item.getId()] = item;
			LOOK_UP[4] = fromId(0);
		}
		
	}
	
	public static enum Activation {
		ACTIVATE_BLOCK(0),
		CLICK_BLOCK(1),
		USE_ITEM(2),
		CLICK_ITEM(3),
		ENTITY(4);

		
		private static final Activation[] LOOK_UP = new Activation[6];
		private int id;
		
		private Activation(int id) {
			this.id = id;
		}
		
		public int getId() {
			return this.id;
		}
		
		public Activation next() {
			return fromId(this.getId() + 1);
		}
		
		@Override
		public String toString() {
			switch (this) {
			case ACTIVATE_BLOCK:
				return "Activate Block";
			case CLICK_BLOCK:
				return "Click Block";
			case CLICK_ITEM:
				return "Item Click";
			case ENTITY:
				return "Entity";
			case USE_ITEM:
				return "Use On Block";
			default:
				return "";
			}
		}
		
		public static Activation fromId(int id) {
			return LOOK_UP[id];
		}
		
		static {
			for (Activation item: values())
				LOOK_UP[item.getId()] = item;
			LOOK_UP[5] = fromId(0);
		}
		
	}
	
	public static enum Button {
		RIGHT(0),
		LEFT(1),
		S_RIGHT(2),
		S_LEFT(3);

		private static final Button[] LOOK_UP = new Button[5];
		private int id;
		
		private Button(int id) {
			this.id = id;
		}
		
		public int getId() {
			return this.id;
		}
		
		public boolean isSneakMode() {
			return this.id > 1;
		}
		
		public Button next() {
			return fromId(this.getId() + 1);
		}
		
		@Override
		public String toString() {
			switch (this) {
			case LEFT:
				return "Left";
			case RIGHT:
				return "Right";
			case S_LEFT:
				return "Sneak Left";
			case S_RIGHT:
				return "Sneak Right";
			default:
				return "";
			}
		}
		
		public static Button fromId(int id) {
			return LOOK_UP[id];
		}
		
		static {
			for (Button item: values())
				LOOK_UP[item.getId()] = item;
			LOOK_UP[4] = fromId(0);
		}
		
	}
	
	public static enum Select {
		UPPER_LEFT(0),
		RANDOM(1);

		private static final Select[] LOOK_UP = new Select[3];
		private int id;
		
		private Select(int id) {
			this.id = id;
		}
		
		public int getId() {
			return this.id;
		}
		
		public Select next() {
			return fromId(this.getId() + 1);
		}
		
		@Override
		public String toString() {
			switch (this) {
			case RANDOM:
				return "Random Slot";
			case UPPER_LEFT:
				return "Upper Left";
			default:
				return "";
			}
		}
		
		public static Select fromId(int id) {
			return LOOK_UP[id];
		}
		
		static {
			for (Select item: values())
				LOOK_UP[item.getId()] = item;
			LOOK_UP[2] = fromId(0);
		}
		
	}
	
	public static enum Redstone {
		ACTIVE_ON_REDSTONE(0),
		ALWAYS_ACTIVE(1),
		ACTIVE_WITHOUT_REDSTONE(2);

		private static final ResourceLocation L_ACTIVE_ON_REDSTONE = new ResourceLocation("blocks/redstone_torch_on");
		private static final ResourceLocation L_ACTIVE_WITHOUT_REDSTONE = new ResourceLocation("blocks/redstone_torch_off");
		private static final ResourceLocation L_ALWAYS_ACTIVE = new ResourceLocation("items/redstone_dust");
		private static final Redstone[] LOOK_UP = new Redstone[4];
		private int id;
		
		private Redstone(int id) {
			this.id = id;
		}
		
		public int getId() {
			return this.id;
		}
		
		public Redstone next() {
			return fromId(this.getId() + 1);
		}
		
		public ResourceLocation getImgLocation() {
			switch (id) {
			case 0:
				return L_ACTIVE_ON_REDSTONE;
			case 1:
				return L_ALWAYS_ACTIVE;
			case 2:
				return L_ACTIVE_WITHOUT_REDSTONE;
			default:
				return L_ACTIVE_ON_REDSTONE;
			}
		}
		
		@Override
		public String toString() {
			switch (this) {
			case ACTIVE_ON_REDSTONE:
				return "Active on redstone";
			case ACTIVE_WITHOUT_REDSTONE:
				return "Active without redstone";
			case ALWAYS_ACTIVE:
				return "Always active";
			default:
				return "";
			}
		}
		
		public static Redstone fromId(int id) {
			return LOOK_UP[id];
		}
		
		static {
			for (Redstone item: values())
				LOOK_UP[item.getId()] = item;
			LOOK_UP[3] = fromId(0);
		}
		
	}
	
	public static class EnergyBank extends EnergyStorage {
		
		public EnergyBank(int capacity) {
			super(capacity);
		}
		
		public EnergyBank(int capacity, int maxReceive) {
			super(capacity, maxReceive);
		}
		
		public EnergyBank(int capacity, int maxReceive, int maxExtract) {
			super(capacity, maxReceive, maxExtract);
		}

		public EnergyBank(int capacity, int maxReceive, int maxExtract, int energy) {
			super(capacity, maxReceive, maxExtract, energy);
		}
		
		public void setEnergy(int amount) {
			if (amount > this.getMaxEnergyStored())
				energy = capacity;
			else 
				energy = amount;
		}
		
	}
	
}
