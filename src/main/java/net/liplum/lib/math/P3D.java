package net.liplum.lib.math;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class P3D {

    public static boolean isInside(Point3D subject, Point3D object, float pitch, float yaw, float roll, AxisAlignedCube cube) {
        DoubleMatrix2D R = genRotateMatrix(pitch, yaw, roll);
        Point3D p = object.minus(subject);
        new DenseDoubleMatrix1D(new double[]{p.x, p.y, p.z, 1});
        return false;
    }

    public static Vec3d from2D(Vector2D v2d) {
        return new Vec3d(v2d.x, v2d.y, 0);
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
     * Gets rotate matrix based on (0,0,0).
     *
     * @param pitch
     * @return 2*2 matrix
     */
    private static DoubleMatrix2D genPitchRotateMatrix(float pitch) {
        double sina = MathHelper.sin(pitch), cosa = MathHelper.cos(pitch);
        return new DenseDoubleMatrix2D(new double[][]{
                {1, 0, 0},
                {0, cosa, -sina},
                {0, sina, cosa}
        });
    }

    /**
     * Gets rotate matrix based on (0,0,0).
     *
     * @param yaw
     * @return 2*2 matrix
     */
    private static DoubleMatrix2D genYawRotateMatrix(float yaw) {
        double sina = MathHelper.sin(yaw), cosa = MathHelper.cos(yaw);
        return new DenseDoubleMatrix2D(new double[][]{
                {cosa, -sina, 0},
                {sina, cosa, 0},
                {0, 0, 1}
        });
    }

    /**
     * Gets rotate matrix based on (0,0,0).
     *
     * @param roll
     * @return 2*2 matrix
     */
    private static DoubleMatrix2D genRollRotateMatrix(float roll) {
        double sina = MathHelper.sin(roll), cosa = MathHelper.cos(roll);
        return new DenseDoubleMatrix2D(new double[][]{
                {cosa, 0, sina},
                {0, 1, 0},
                {sina, 0, cosa}
        });
    }

    /**
     * Gets rotate matrix based on (0,0,0).
     *
     * @param pitch
     * @param yaw
     * @param roll
     * @return 2*2 matrix
     */
    private static DoubleMatrix2D genRotateMatrix(float pitch, float yaw, float roll) {
        return Algebra.DEFAULT.mult(
                Algebra.DEFAULT.mult(
                        genPitchRotateMatrix(pitch), genYawRotateMatrix(yaw))
                , genRollRotateMatrix(roll));
    }
}
