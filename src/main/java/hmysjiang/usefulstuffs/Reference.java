package hmysjiang.usefulstuffs;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

public class Reference {

	public static final String MOD_ID = "usefulstuffs";
	public static final String NAME = "Useful Stuffs";
	public static final String VERSION = "2.5.0";
	public static final String ACCEPTED_MCVERSION = "[1.12.2]";
	public static final boolean TEST_MODE = false;
	public static final GameProfile MOD_PROFILE = new GameProfile(UUID.fromString("9b607828-0f15-4ceb-ba58-708fa1f22009"), "UsefulStuffsFakePlayer");
	
	public static enum ModItems{
		MAGICAL_WAND("magical_wand"),
		LIGHT_SHOOTER("light_shooter"),
		LIGHT_SHOOTER_COLLECTOR("light_shooter_collector"),
		WATERFILTER("waterfilter"),
		UMBRELLA("umbrella"),
		CSU("compact_storage_unit"),
		BENTO("bento"),
		BUILDING_WAND("building_wand"),
		BUILDING_WAND_INFINITE("building_wand_infinite"),
		BUILDING_WIZARD("building_wizard"),
		BODY_STORAGE("storage_body"),
		BELT_LILY("lily_belt"),
		PACKING_GLUE("packing_glue"),
		FLIPFLOPCORE("flipflop_core"),
		BERRY("berry"),
		BELT_FIERY_LILY("fiery_lily_belt"),
		BODY_BACKPACK("backpack"),
		BODY_BACKPACK_MINING("mining_backpack"),
		INIFNITE_WATER("infinite_water"),
		MILK_BAG("milk_bag"),
		CHEESE("cheese"),
		LIGHT_BOW("light_bow"),
		LIGHT_BATTERY("light_battery"),
		TRIANGULAR_PRISM("triangular_prism"),
		UNIVERSAL_CORE("universal_core");
		
		private String unlocalizedName;
		
		ModItems(String unlocalizedName) {
			this.unlocalizedName = unlocalizedName;
		}
		
		public String getUnlocalizedName() {
			return MOD_ID + "." + this.unlocalizedName;
		}
		
		public String getRegistryName() {
			return this.unlocalizedName;
		}
		
	}
	
	public static enum ModBlocks{
		LIGHT_BULB("light_bulb"),
		WELL("well"),
		RAIN_DETECTOR("rain_detector"),
		CAMPFIRE("campfire"),
		FILING_CABINET("filing_cabinet"),
		GLUED_BOX("glued_box"),
		T_FLIPFLOP("t_flipflop"),
		BERRYBUSH("berrybush"),
		PORTAL_MUFFLER("portal_muffler"),
		PLAYER_DETECTOR("player_detector"),
		FIERY_LILY("fiery_lily"),
		MILK_FERMENTER("milk_fermenter"),
		UNIVERSAL_USER("universal_user");
		
		private String unlocalizedName;
		
		ModBlocks(String unlocalizedName) {
			this.unlocalizedName = unlocalizedName;
		}
		
		public String getUnlocalizedName() {
			return MOD_ID + "." + this.unlocalizedName;
		}
		
		public String getRegistryName() {
			return this.unlocalizedName;
		}
		
	}
	
	public static enum ModEntities{
		LIGHT_BULB(0),
		LIGHT_ARROW(1);
		
		private int ID;
		
		ModEntities(int ID) {
			this.ID = ID;
		}
		
		public int getID() {
			return this.ID;
		}
	}

}
