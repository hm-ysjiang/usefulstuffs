package hmysjiang.usefulstuffs.potion.potiontype;

import javax.annotation.Nullable;

import hmysjiang.usefulstuffs.Reference;
import hmysjiang.usefulstuffs.potion.PotionFieryLily;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;

public class PotionTypeFieryLily extends PotionType {
	
	public static PotionType instance = new PotionTypeFieryLily("fierylily", new PotionEffect(PotionFieryLily.instance, 3000));
	public static PotionType instance_long = new PotionTypeFieryLily("fierylily_long", "fierylily", new PotionEffect(PotionFieryLily.instance, 5000));
	
	public PotionTypeFieryLily(String registryName, PotionEffect... effects) {
		this(registryName, null, effects);
	}
	
	public PotionTypeFieryLily(String registryName, @Nullable String baseName, PotionEffect... effects) {
		super(baseName, effects);
		setRegistryName(new ResourceLocation(Reference.MOD_ID, registryName));
	}
	
}
