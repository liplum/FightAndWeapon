package net.liplum.lib.math;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class P3D {

    public static boolean isInside(Point3D subject, Point3D object, float pitch, float yaw, AxisAlignedCube cube) {
        Point3D p = object.minus(subject);
        pitch = (float) Angle.getRotationOrItsSupplementary(pitch, 0);
        yaw = (float) Angle.getRotationOrItsSupplementary(yaw, 0);
        DoubleMatrix1D A = rotate((float) p.x, (float) p.y, (float) p.z, pitch, yaw);
        Point3D res = new Point3D(A.get(0), A.get(1), A.get(2));
        return cube.isInside(res);
    }

    /**
     * Rotate the point with pitch and yaw but not roll
     */
    public static DoubleMatrix1D rotate(float px, float py, float pz, float pitch, float yaw, float roll) {
        DenseDoubleMatrix1D A = new DenseDoubleMatrix1D(new double[]{px, py, pz, 1});
        return Algebra.DEFAULT.mult(genRotateMatrix(pitch, yaw, roll), A);
    }

    /**
     * Rotate the point with pitch, yaw and roll
     */
    public static DoubleMatrix1D rotate(float px, float py, float pz, float pitch, float yaw) {
        DenseDoubleMatrix1D A = new DenseDoubleMatrix1D(new double[]{px, py, pz, 1});
        DoubleMatrix2D R = Algebra.DEFAULT.mult(genPitchRotateMatrix(pitch), genYawRotateMatrix(yaw));
        return Algebra.DEFAULT.mult(R, A);
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
                {1, 0, 0, 0},
                {0, cosa, -sina, 0},
                {0, sina, cosa, 0},
                {0, 0, 0, 1}
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
                {cosa, sina, 0, 0},
                {-sina, cosa, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
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
                {cosa, 0, sina, 0},
                {0, 1, 0, 0},
                {-sina, 0, cosa, 0},
                {0, 0, 0, 1}
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


    public static Point3D toPosition(@NotNull Entity e) {
        return new Point3D(e.posX, e.posY, e.posZ);
    }
}
