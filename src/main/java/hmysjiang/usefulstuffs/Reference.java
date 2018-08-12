package hmysjiang.usefulstuffs;

public class Reference {

	public static final String MOD_ID = "usefulstuffs";
	public static final String NAME = "Useful Stuffs";
	public static final String VERSION = "2.1.1";
	public static final String ACCEPTED_MCVERSION = "[1.12.2]";
	public static final boolean TEST_MODE = false;
	
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
		BERRY("berry");
		
		private String unlocalizedName;
		
		ModItems(String unlocalizedName) {
			this.unlocalizedName = unlocalizedName;
		}
		
		public String getUnlocalizedName() {
			return MOD_ID+"."+this.unlocalizedName;
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
		BERRYBUSH("berrybush");
		
		private String unlocalizedName;
		
		ModBlocks(String unlocalizedName) {
			this.unlocalizedName = unlocalizedName;
		}
		
		public String getUnlocalizedName() {
			return MOD_ID+"."+this.unlocalizedName;
		}
		
		public String getRegistryName() {
			return this.unlocalizedName;
		}
		
	}
	
	public static enum ModEntities{
		LIGHT_BULB(0);
		
		private int ID;
		
		ModEntities(int ID) {
			this.ID = ID;
		}
		
		public int getID() {
			return this.ID;
		}
	}

}
