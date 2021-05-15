package net.liplum.lib.math;

import net.minecraft.util.math.MathHelper;

public class Point {
    public final double x;
    public final double y;
    public static final Point Zero = new Point();

    public Point() {
        this(0, 0);
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D direct(Point point) {
        return new Vector2D(point.x - x, point.y - y);
    }

    public double abs(Point point) {
        double dx = point.x - x;
        double dy = point.y - y;
        return MathHelper.sqrt(dx * dx + dy * dy);
    }

    public Point minus(Point other) {
        return new Point(x - other.x, y - other.y);
    }

    public Point add(Point other) {
        return new Point(x + other.x, y + other.y);
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

    private boolean isZero() {
        return x == 0 && y == 0;
    }

    public Vector2D toV2D() {
        return new Vector2D(x, y);
    }
}
