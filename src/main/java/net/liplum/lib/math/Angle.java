package net.liplum.lib.math;

public class Angle {

    public static final double HalfPI = Math.PI / 2;
    public static final double PI = Math.PI;
    private static final double PI_Divide_180 = PI / 180;
    private static final float PI_Divide_180_Float = (float) (PI / 180);

    public static double toRadian(double degrees) {
        return degrees % 360 * PI_Divide_180;
    }

    public static float toRadian(float degrees) {
        return degrees % 360 * PI_Divide_180_Float;
    }

    /**
     * @param radius
     * @return (Unit : Radius)
     */
    public static double supplementary(double radius) {
        return 2 * Math.PI - radius;
    }

    /**
     * Get a radius
     *
     * @param radius the radius [0,2π]
     * @return the radius [0,π]
     */
    public static double getRotationOrItsSupplementary(double radius, double target) {
        double afterHalfCircle = radius + PI;
        if (target <= afterHalfCircle) {
            return target - radius;
        }
        return supplementary(radius - target);
    }

    /**
     * @param radius
     * @return (Unit : Radius)
     */
    public static float supplementary(float radius) {
        return 2 * (float) (Math.PI) - radius;
    }

    public static float toNormalDegreeAngle(float minecraftAngle) {
        if (minecraftAngle <= 0) {
            return 180 + minecraftAngle;
        }
        return minecraftAngle;
    }

}
