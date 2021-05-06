package net.liplum.lib.math;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import com.sun.javafx.geom.Path2D;
import com.sun.javafx.geom.Shape;
import javafx.scene.shape.Polygon;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;


public class MathTool {
    private static final double PI_Divide_180 = Math.PI / 180;
    public static final double HalfPI = Math.PI / 2;
    public static final Vector2D iY = new Vector2D(0, 1);

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

    public static Vector2D toV2D(Vec3d v3d) {
        return new Vector2D(v3d.x, v3d.z);
    }

    public static boolean isInside(Vector2D look, Point player, Point target, double width, double length) {
        Vector2D look_ = look.normalized();
        Point deltaP = target.minus(player);
        double deltaX = deltaP.x, deltaY = deltaP.y;
        float a = (float) iY.angle(look_);
        DoubleMatrix1D A = new DenseDoubleMatrix1D(new double[]{target.x, target.y,1});
        DoubleMatrix1D Result = Algebra.DEFAULT.mult(
                genRotateMatrix(deltaX,deltaY,a)
                , A);
        double halfw = width / 2, x = player.x, y = player.y;
        return MathTool.belongToCC(x - halfw, x + halfw, Result.get(0)) &&
                MathTool.belongToCC(y, y + length, Result.get(1));
    }

    private static DoubleMatrix2D genRotateMatrix(double deltaX, double deltaY, float angleA) {
        double sina = MathHelper.sin(angleA), cosa = MathHelper.cos(angleA);
        DoubleMatrix2D T1 = Algebra.DEFAULT.mult(
                new DenseDoubleMatrix2D(new double[][]{{1, 0, deltaX}, {0, 1, deltaY}, {0, 0, 1}}),
                new DenseDoubleMatrix2D(new double[][]{{cosa,-sina,0},{sina,cosa,0},{0,0,1}}));
        return Algebra.DEFAULT.mult(T1,
                new DenseDoubleMatrix2D(new double[][]{{1,0,-deltaX},{0,1,-deltaY},{0,0,1}}));
    }

    public static int fixMax(int value, int max) {
        return value > max ? max : value;
    }

    public static int fixMin(int value, int min) {
        return value < min ? min : value;
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
        return value > max ? max : value;
    }

    public static float fixMin(float value, float min) {
        return value < min ? min : value;
    }

    public static double fixMax(double value, double max) {
        return value > max ? max : value;
    }

    public static double fixMin(double value, double min) {
        return value < min ? min : value;
    }

    public static long fixMax(long value, long max) {
        return value > max ? max : value;
    }

    public static long fixMin(long value, long min) {
        return value < min ? min : value;
    }
}
