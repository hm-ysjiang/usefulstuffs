package hmysjiang.usefulstuffs;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

public class Reference {
	
	public static final String MOD_ID = "usefulstuffs";
	public static final String NAME = "Useful Stuffs";
	public static final String VERSION = "1.0alpha";
	public static final String ACCEPTED_MCVERSION = "[1.10.2]";
	
	public static enum ModItems{
		MAGICAL_WAND("magical_wand"),
		LIGHT_SHOOTER("light_shooter"),
		LIGHT_SHOOTER_COLLECTOR("light_shooter_collector"),
		WATERFILTER("waterfilter"),
		UMBRELLA("umbrella"),
		CSU("compact_storage_unit"),
		HAND_GUN("gun_hand"),
		BULLET("bullet"),
		BULLET_HEAD("bullet_head"),
		BENTO("bento");
		
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
		LANTERN("lantern"),
		FILING_CABINET("filing_cabinet");
		
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
		LIGHT_BULB(0),
		FAIRY_LIGHT(1),
		BULLET(2);
		
		private int ID;
		
		ModEntities(int ID) {
			this.ID = ID;
		}
		
		public int getID() {
			return this.ID;
		}
	}

}