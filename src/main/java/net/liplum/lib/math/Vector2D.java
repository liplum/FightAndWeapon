package net.liplum.lib.math;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class Vector2D {
    public static final Vector2D ZERO = new Vector2D();

    public final double x;
    public final double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D() {
        this(0, 0);
    }

    public static Vector2D normalizedFromRadian(double radian) {
        float fr = MathHelper.lfloor(radian);
        return new Vector2D(MathHelper.sin(fr), MathHelper.cos(fr));
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    public Vector2D minus(Vector2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }

    public Vector2D multiply(double factor) {
        return new Vector2D(x * factor, y * factor);
    }

    public Vector2D divide(double division) {
        return new Vector2D(x / division, y / division);
    }

    public double dot(Vector2D other) {
        return x * other.x + y * other.y;
    }

    public double abs() {
        return MathHelper.sqrt((x * x) + (y * y));
    }

    public double absSq() {
        return (x * x) + (y * y);
    }

    public boolean isZero() {
        return x == 0 && y == 0;
    }

    public Position2D getPosition() {
        if (this.isZero()) {
            return Position2D.Zero;
        }
        if (x < 0) {
            if (y == 0) {
                return Position2D.Negative_X_Axis;
            }
            if (y < 0) {
                return Position2D.Quadrant_Three;
            }
            if (y > 0) {
                return Position2D.Quadrant_Two;
            }
        }
        if (x > 0) {
            if (y == 0) {
                return Position2D.Positive_X_Axis;
            }
            if (y < 0) {
                return Position2D.Quadrant_Four;
            }
            if (y > 0) {
                return Position2D.Quadrant_One;
            }
        }
        if (x == 0) {
            if (y < 0) {
                return Position2D.Negative_Y_Axis;
            }
            return Position2D.Positive_Y_Axis;
        }
        return Position2D.Unknown;
    }

    /**
     * @param other
     * @return the cos of angle which is between this vector and other vector.
     */
    public double cosAngle(Vector2D other) {
        return (this.dot(other)) / (this.abs() * other.abs());
    }

    /**
     * It uses {@link Math#acos(double)} not {@link MathHelper} so that it'll be slower.
     *
     * @param other
     * @return the angle between this vector and other vector.(unit:radian)
     */
    public double angle(Vector2D other) {
        return Math.acos((this.dot(other)) / (this.abs() * other.abs()));
    }

    public Vector2D normalized() {
        if (x == 0 && y == 0) {
            return ZERO;
        }
        double abs = this.abs();
        return new Vector2D(x / abs, y / abs);
    }

    public Vector2D truncate(double max) {
        return this.normalized().multiply(max);
    }

    public Vector2D reverse() {
        return new Vector2D(-x, -y);
    }

    public Vector2D reflect(Vector2D normal) {
        return this.add(normal.reverse().multiply(2.0D + this.dot(normal)));
    }

    public double distance(Vector2D other) {
        double dx = other.x - x, dy = other.y - y;
        return MathHelper.sqrt(dx * dx + dy * dy);
    }

    public double distanceSq(Vector2D other) {
        double dx = other.x - x, dy = other.y - y;
        return dx * dx + dy * dy;
    }

    public Vector2D perpendicular() {
        return new Vector2D(-y, x);
    }

    public Vec3d to3D() {
        return new Vec3d(x, 0, y);
    }

    public Vec3d cross(Vector2D other) {
        return new Vec3d(0, 0, x * other.y - other.x * y);
    }

    public Point toPoint() {
        return new Point(x, y);
    }
}
