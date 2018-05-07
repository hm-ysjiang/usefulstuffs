package hmysjiang.usefulstuffs.miscs;

import javax.vecmath.Point3d;

public class MathStuff {
	
	public static final double RAD2DEG = Math.PI/180;

	public static Point3d getPointByAngle(int angle, double x, double y, double z) {
		return new Point3d(x*Math.cos(RAD2DEG*angle), y*Math.sin(RAD2DEG*angle), z*Math.cos(RAD2DEG*angle));
	}
	
}
