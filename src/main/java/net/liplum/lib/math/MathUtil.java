package net.liplum.lib.math;

import net.liplum.lib.utils.Utils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;


public class MathUtil {

    public static boolean isAngleLessThan(Vector2D v1, Vector2D v2, double radian) {
        double cosA = v1.cosAngle(v2);
        return cosA > MathHelper.cos((float) radian);
    }

    public static Vec3d toEntityFace(Vec3d v3d) {
        return new Vec3d(-v3d.x, v3d.y, v3d.z);
    }

    public static int castTo(int min, int max, int value) {
        return fixMax(fixMin(value, min), max);
    }

    public static float castTo(float min, float max, float value) {
        return fixMax(fixMin(value, min), max);
    }

    public static double castTo(double min, double max, double value) {
        return fixMax(fixMin(value, min), max);
    }

    public static boolean belongToOO(double left, double right, double value) {
        double min = Math.min(left, right), max = Math.max(left, right);
        return value > min && value < max;
    }

    public static boolean belongToOC(double left, double right, double value) {
        double min = Math.min(left, right), max = Math.max(left, right);
        return value > min && value <= max;
    }

    public static boolean belongToCO(double left, double right, double value) {
        double min = Math.min(left, right), max = Math.max(left, right);
        return value >= min && value < max;
    }

    public static boolean belongToCC(double left, double right, double value) {
        double min = Math.min(left, right), max = Math.max(left, right);
        return value >= min && value <= max;
    }

    public static boolean belongToOO(float left, float right, float value) {
        float min = Math.min(left, right), max = Math.max(left, right);
        return value > min && value < max;
    }

    public static boolean belongToOC(float left, float right, float value) {
        float min = Math.min(left, right), max = Math.max(left, right);
        return value > min && value <= max;
    }

    public static boolean belongToCO(float left, float right, float value) {
        float min = Math.min(left, right), max = Math.max(left, right);
        return value >= min && value < max;
    }

    public static boolean belongToCC(float left, float right, float value) {
        float min = Math.min(left, right), max = Math.max(left, right);
        return value >= min && value <= max;
    }

    public static boolean belongToOO(int left, int right, int value) {
        float min = Math.min(left, right), max = Math.max(left, right);
        return value > min && value < max;
    }

    public static boolean belongToOC(int left, int right, int value) {
        float min = Math.min(left, right), max = Math.max(left, right);
        return value > min && value <= max;
    }

    public static boolean belongToCO(int left, int right, int value) {
        float min = Math.min(left, right), max = Math.max(left, right);
        return value >= min && value < max;
    }

    public static boolean belongToCC(int left, int right, int value) {
        float min = Math.min(left, right), max = Math.max(left, right);
        return value >= min && value <= max;
    }

    public static int fixMax(int value, int max) {
        return Math.min(value, max);
    }

    public static int fixMin(int value, int min) {
        return Math.max(value, min);
    }

    public static short fixMax(short value, short max) {
        return value > max ? max : value;
    }

    public static short fixMin(short value, short min) {
        return value < min ? min : value;
    }

    public static byte fixMax(byte value, byte max) {
        return value > max ? max : value;
    }

    public static byte fixMin(byte value, byte min) {
        return value < min ? min : value;
    }

    public static float fixMax(float value, float max) {
        return Math.min(value, max);
    }

    public static float fixMin(float value, float min) {
        return Math.max(value, min);
    }

    public static double fixMax(double value, double max) {
        return Math.min(value, max);
    }

    public static double fixMin(double value, double min) {
        return Math.max(value, min);
    }

    public static long fixMax(long value, long max) {
        return Math.min(value, max);
    }

    public static long fixMin(long value, long min) {
        return Math.max(value, min);
    }

    public static Point2D to2DPoint(Vec3d v3d) {
        return new Point2D(v3d.x, v3d.z);
    }

    public static int randomFloor(double value) {
        int res = (int) Math.floor(value);
        return res + Utils.getRandom().nextInt(1);
    }

    /**
     * {@code round(12345,2) == 123}
     *
     * @param number the number
     * @param digit  it decides how many Digit the result will be removed and mustn't be negative
     * @return the result
     */
    public static int removeDigit(int number, int digit) {
        digit = fixMin(digit, 0);
        return digit == 0 ?
                number :
                number / (digit * 10);
    }

}
