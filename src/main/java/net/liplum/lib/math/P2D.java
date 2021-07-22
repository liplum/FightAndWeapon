package net.liplum.lib.math;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nonnull;

public class P2D {

    public static final Vector2D iY = new Vector2D(0, 1);
    public static final Vector2D iX = new Vector2D(1, 0);

    @Nonnull
    public static Vector2D toV2D(@Nonnull Vec3d v3d) {
        return new Vector2D(v3d.x, v3d.z);
    }

    /**
     * @param xy
     * @param rotationMatrix
     * @return
     */
    public static DoubleMatrix1D rotate(DoubleMatrix1D xy, DoubleMatrix2D rotationMatrix) {
        return Algebra.DEFAULT.mult(rotationMatrix, xy);
    }

    public static boolean isInside(Vector2D look, Point2D player, Point2D target, double width, double length) {
        if (look.isZero()) {
            return false;
        }
        Vector2D l = look.normalized();
        Point2D Ep = target.minus(player);
        DoubleMatrix1D A = new DenseDoubleMatrix1D(new double[]{Ep.x, Ep.y});
        float o = (float) iX.angle(l);
        float a;
        Position2D lp = l.getPosition();
        if (lp == Position2D.Quadrant_One || lp == Position2D.Quadrant_Two || lp == Position2D.Positive_Y_Axis) {
            a = MathUtil.supplementary(o);
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
                new DenseDoubleMatrix2D(new double[][]{
                        {1, 0, deltaX},
                        {0, 1, deltaY},
                        {0, 0, 1}
                }),
                new DenseDoubleMatrix2D(new double[][]{
                        {cosa, -sina, 0},
                        {sina, cosa, 0},
                        {0, 0, 1}
                }));
        return Algebra.DEFAULT.mult(T1,
                new DenseDoubleMatrix2D(new double[][]{
                        {1, 0, -deltaX},
                        {0, 1, -deltaY},
                        {0, 0, 1}
                }));
    }

    /**
     * Gets rotate matrix based on (0,0).
     *
     * @param angleA
     * @return 2*2 matrix
     */
    private static DoubleMatrix2D genRotateMatrix(float angleA) {
        double sina = MathHelper.sin(angleA), cosa = MathHelper.cos(angleA);
        return new DenseDoubleMatrix2D(new double[][]{
                {cosa, -sina},
                {sina, cosa}
        });
    }
}
