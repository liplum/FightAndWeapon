package net.liplum.lib.math;

public class AxisAlignedCube {

    public final double minX;
    public final double minY;
    public final double minZ;
    public final double maxX;
    public final double maxY;
    public final double maxZ;

    public AxisAlignedCube(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.minX = Math.min(x1, x2);
        this.minY = Math.min(y1, y2);
        this.minZ = Math.min(z1, z2);
        this.maxX = Math.max(x1, x2);
        this.maxY = Math.max(y1, y2);
        this.maxZ = Math.max(z1, z2);
    }

    public boolean isInside(Point3D p) {
        return MathUtil.belongToCC(minX, maxX, p.x) &&
                MathUtil.belongToCC(minY, maxY, p.y) &&
                MathUtil.belongToCC(minZ, maxZ, p.z);
    }
}
