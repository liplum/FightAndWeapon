package net.liplum.lib.math;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Point3D {
    public static final Point3D Zero = new Point3D();
    public final double x;
    public final double y;
    public final double z;

    public Point3D() {
        this(0, 0, 0);
    }

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3d direct(Point3D point) {
        return new Vec3d(point.x - x, point.y - y, point.z - z);
    }

    public double abs(Point3D point) {
        double dx = point.x - x;
        double dy = point.y - y;
        double dz = point.z - z;
        return MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public Point3D minus(Point3D other) {
        return new Point3D(x - other.x, y - other.y, z - other.z);
    }

    public Point3D add(Point3D other) {
        return new Point3D(x + other.x, y + other.y, z + other.z);
    }

    private boolean isZero() {
        return this.equals(Zero);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point3D) {
            Point3D v = (Point3D) obj;
            return v.x == x &&
                    v.y == y &&
                    v.z == z;
        }
        return false;
    }

    public Vector2D toV2D() {
        return new Vector2D(x, y);
    }
}
