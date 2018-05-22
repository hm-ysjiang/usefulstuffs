package hmysjiang.usefulstuffs;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

public class Reference {
	
	public static final String MOD_ID = "usefulstuffs";
	public static final String NAME = "Useful Stuffs";
	public static final String VERSION = "1.0alpha";
	public static final String ACCEPTED_MCVERSION = "[1.10.2]";
	
	public static enum ModItems{
		MAGICAL_WAND("magical_wand", "ItemMagicalWand"),
		LIGHT_SHOOTER("light_shooter", "ItemLightShooter"),
		LIGHT_SHOOTER_COLLECTOR("light_shooter_collector", "ItemLightShooterCollector"),
		WATERFILTER("waterfilter", "ItemWaterFilter"),
		UMBRELLA("umbrella", "ItemUmbrella");
		
		private String unlocalizedName;
		private String registryName;
		
		ModItems(String unlocalizedName, String registryName) {
			this.unlocalizedName = unlocalizedName;
			this.registryName = registryName;
		}
		
		public String getUnlocalizedName() {
			return MOD_ID+"."+this.unlocalizedName;
		}
		
		public String getRegistryName() {
			return this.registryName;
		}
		
	}
	
	public static enum ModBlocks{
		LIGHT_BULB("light_bulb", "BlockLightBulb"),
		WELL("well", "BlockWell"),
		RAIN_DETECTOR("rain_detector", "BlockRainDetector"),
		CAMPFIRE("campfire", "BlockCampfire");
		
		private String unlocalizedName;
		private String registryName;
		
		ModBlocks(String unlocalizedName, String registryName) {
			this.unlocalizedName = unlocalizedName;
			this.registryName = registryName;
		}
		
		public String getUnlocalizedName() {
			return MOD_ID+"."+this.unlocalizedName;
		}
		
		public String getRegistryName() {
			return this.registryName;
		}
		
	}
	
	public static enum ModEntities{
		LIGHT_BULB(0),
		FAIRY_LIGHT(1);
		
		private int ID;
		
		ModEntities(int ID) {
			this.ID = ID;
		}
		
		public int getID() {
			return this.ID;
		}
	}

}