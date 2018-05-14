package hmysjiang.usefulstuffs.miscs.helpers;

import net.minecraft.util.math.Vec3d;

public class MathHelper {
	
	public static final double RAD2DEG = Math.PI/180;

	public static Vec3d getPointByAngle(int angle, double x, double y, double z) {
		return new Vec3d(x*Math.cos(RAD2DEG*angle), y*Math.sin(RAD2DEG*angle), z*Math.cos(RAD2DEG*angle));
	}
	
}