package net.liplum.lib.math;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;


public class MathUtil {
    public static final double HalfPI = Math.PI / 2;
    public static final Vector2D iY = new Vector2D(0, 1);
    public static final Vector2D iX = new Vector2D(1, 0);
    private static final double PI_Divide_180 = Math.PI / 180;

    public static double toRadian(double degrees) {
        return degrees % 360 * PI_Divide_180;
    }

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
        if ((value == left) && (value == right)) {
            return true;
        }
        double min = Math.min(left, right), max = Math.max(left, right);
        if (value > min && value < max) {
            return true;
        }
        return false;
    }

    public static boolean belongToOC(double left, double right, double value) {
        if ((value == left) && (value == right)) {
            return true;
        }
        double min = Math.min(left, right), max = Math.max(left, right);
        if (value > min && value <= max) {
            return true;
        }
        return false;
    }

    public static boolean belongToCO(double left, double right, double value) {
        if ((value == left) && (value == right)) {
            return true;
        }
        double min = Math.min(left, right), max = Math.max(left, right);
        if (value >= min && value < max) {
            return true;
        }
        return false;
    }

    public static boolean belongToCC(double left, double right, double value) {
        if ((value == left) && (value == right)) {
            return true;
        }
        double min = Math.min(left, right), max = Math.max(left, right);
        if (value >= min && value <= max) {
            return true;
        }
        return false;
    }

    public static boolean belongToOO(float left, float right, float value) {
        if ((value == left) && (value == right)) {
            return true;
        }
        float min = Math.min(left, right), max = Math.max(left, right);
        if (value > min && value < max) {
            return true;
        }
        return false;
    }

    public static boolean belongToOC(float left, float right, float value) {
        if ((value == left) && (value == right)) {
            return true;
        }
        float min = Math.min(left, right), max = Math.max(left, right);
        if (value > min && value <= max) {
            return true;
        }
        return false;
    }

    public static boolean belongToCO(float left, float right, float value) {
        if ((value == left) && (value == right)) {
            return true;
        }
        float min = Math.min(left, right), max = Math.max(left, right);
        if (value >= min && value < max) {
            return true;
        }
        return false;
    }

    public static boolean belongToCC(float left, float right, float value) {
        if ((value == left) && (value == right)) {
            return true;
        }
        float min = Math.min(left, right), max = Math.max(left, right);
        if (value >= min && value <= max) {
            return true;
        }
        return false;
    }

    public static boolean belongToOO(int left, int right, int value) {
        if ((value == left) && (value == right)) {
            return true;
        }
        float min = Math.min(left, right), max = Math.max(left, right);
        if (value > min && value < max) {
            return true;
        }
        return false;
    }

    public static boolean belongToOC(int left, int right, int value) {
        if ((value == left) && (value == right)) {
            return true;
        }
        float min = Math.min(left, right), max = Math.max(left, right);
        if (value > min && value <= max) {
            return true;
        }
        return false;
    }

    public static boolean belongToCO(int left, int right, int value) {
        if ((value == left) && (value == right)) {
            return true;
        }
        float min = Math.min(left, right), max = Math.max(left, right);
        if (value >= min && value < max) {
            return true;
        }
        return false;
    }

    public static boolean belongToCC(int left, int right, int value) {
        if ((value == left) && (value == right)) {
            return true;
        }
        float min = Math.min(left, right), max = Math.max(left, right);
        if (value >= min && value <= max) {
            return true;
        }
        return false;
    }

    public static Vector2D toV2D(Vec3d v3d) {
        return new Vector2D(v3d.x, v3d.z);
    }

    public static boolean isInsideOld(Vector2D look, Point player, Point target, double width, double length) {
        Vector2D look_ = look.normalized();
        Point deltaP = target.minus(player);
        double deltaX = deltaP.x, deltaY = deltaP.y;
        float a = (float) iY.angle(look_);
        DoubleMatrix1D A = new DenseDoubleMatrix1D(new double[]{target.x, target.y, 1});
        DoubleMatrix1D Result = Algebra.DEFAULT.mult(
                genRotateMatrix(deltaX, deltaY, a)
                , A);
        double halfw = width / 2, x = player.x, y = player.y;
        return MathUtil.belongToCC(x - halfw, x + halfw, Result.get(0)) &&
                MathUtil.belongToCC(y, y + length, Result.get(1));
    }

    /**
     * @param xy
     * @param rotationMatrix
     * @return
     */
    public static DoubleMatrix1D rotate(DoubleMatrix1D xy, DoubleMatrix2D rotationMatrix) {
        return Algebra.DEFAULT.mult(rotationMatrix, xy);
    }

    public static boolean isInside(Vector2D look, Point player, Point target, double width, double length) {
        if (look.isZero()) {
            return false;
        }
        Vector2D l = look.normalized();
        Point Ep = target.minus(player);
        DoubleMatrix1D A = new DenseDoubleMatrix1D(new double[]{Ep.x, Ep.y});
        float o = (float) iX.angle(l);
        float a;
        Position2D lp = l.getPosition();
        if (lp == Position2D.Quadrant_One || lp == Position2D.Quadrant_Two || lp == Position2D.Positive_Y_Axis) {
            a = supplementary(o);
        } else {
            a = o;
        }
        DoubleMatrix1D Result = rotate(A, genRotateMatrix(a));
        double halfw = width / 2;
        return MathUtil.belongToCC(0, length, Result.get(0)) &&
                MathUtil.belongToCC(-halfw, halfw, Result.get(1));
    }

    /**
     * Gets rotate matrix based on (deltaX,deltaY).
     *
     * @param deltaX
     * @param deltaY
     * @param angleA
     * @return 3*3 matrix
     */
    private static DoubleMatrix2D genRotateMatrix(double deltaX, double deltaY, float angleA) {
        double sina = MathHelper.sin(angleA), cosa = MathHelper.cos(angleA);
        DoubleMatrix2D T1 = Algebra.DEFAULT.mult(
                new DenseDoubleMatrix2D(new double[][]{{1, 0, deltaX}, {0, 1, deltaY}, {0, 0, 1}}),
                new DenseDoubleMatrix2D(new double[][]{{cosa, -sina, 0}, {sina, cosa, 0}, {0, 0, 1}}));
        return Algebra.DEFAULT.mult(T1,
                new DenseDoubleMatrix2D(new double[][]{{1, 0, -deltaX}, {0, 1, -deltaY}, {0, 0, 1}}));
    }

    /**
     * Gets rotate matrix based on (0,0).
     *
     * @param angleA
     * @return 2*2 matrix
     */
    private static DoubleMatrix2D genRotateMatrix(float angleA) {
        double sina = MathHelper.sin(angleA), cosa = MathHelper.cos(angleA);
        return new DenseDoubleMatrix2D(new double[][]{{cosa, -sina}, {sina, cosa}});
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

    public static Vec3d from2D(Vector2D v2d) {
        return new Vec3d(v2d.x, v2d.y, 0);
    }

    public static Point to2DPoint(Vec3d v3d) {
        return new Point(v3d.x, v3d.z);
    }

    public static Vec3d minus(Vec3d a, Vec3d b) {
        return a.addVector(-b.x, -b.y, -b.z);
    }

    public static Vec3d reserve(Vec3d v3d) {
        return new Vec3d(-v3d.x, -v3d.y, -v3d.z);
    }

    public static Vec3d divide(Vec3d v3d, double division) {
        return new Vec3d(v3d.x / division, v3d.y / division, v3d.z / division);
    }

    /**
     * @param angle
     * @return (Unit : Radius)
     */
    public static double supplementary(double angle) {
        return 2 * Math.PI - angle;
    }

    /**
     * @param angle
     * @return (Unit : Radius)
     */
    public static float supplementary(float angle) {
        return 2 * (float) (Math.PI) - angle;
    }
}
