package hmysjiang.usefulstuffs.potion.potiontype;

import javax.annotation.Nullable;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.potion.PotionUmbrella;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;

public class PotionTypeUmbrella extends PotionType {

	public static PotionType instance = new PotionTypeUmbrella("umbrella", new PotionEffect[] {new PotionEffect(PotionUmbrella.instance, 3600)});
	public static PotionType instance_strong = new PotionTypeUmbrella("umbrella_strong", "umbrella", new PotionEffect[] {new PotionEffect(PotionUmbrella.instance, 1200, 1)});
	public static PotionType instance_long = new PotionTypeUmbrella("umbrella_long", "umbrella", new PotionEffect[] {new PotionEffect(PotionUmbrella.instance, 9600)});
	
	public PotionTypeUmbrella(String registryName, PotionEffect... effects) {
		this(registryName, null, effects);
	}
	
	public PotionTypeUmbrella(String registryName, @Nullable String baseName, PotionEffect... effects) {
		super(baseName, effects);
		setRegistryName(new ResourceLocation(Reference.MOD_ID, registryName));
	}
	
}
