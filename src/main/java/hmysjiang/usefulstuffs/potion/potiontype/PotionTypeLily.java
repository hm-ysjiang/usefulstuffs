package hmysjiang.usefulstuffs.potion.potiontype;

import javax.annotation.Nullable;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.potion.PotionLily;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;

public class PotionTypeLily extends PotionType {
	
	public static PotionType instance = new PotionTypeLily("lily", new PotionEffect[] {new PotionEffect(PotionLily.instance, 12000)});
	public static PotionType instance_long = new PotionTypeLily("lily_long", "lily", new PotionEffect[] {new PotionEffect(PotionLily.instance, 24000)});
	
	public PotionTypeLily(String registryName, PotionEffect... effects) {
		this(registryName, null, effects);
	}
	
	public PotionTypeLily(String registryName, @Nullable String baseName, PotionEffect... effects) {
		super(baseName, effects);
		setRegistryName(new ResourceLocation(Reference.MOD_ID, registryName));
	}
	
}
