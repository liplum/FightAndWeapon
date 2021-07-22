package net.liplum.lib.math;

import net.minecraft.util.math.MathHelper;

public class Point2D {
    public static final Point2D Zero = new Point2D();
    public final double x;
    public final double y;

    public Point2D() {
        this(0, 0);
    }

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D direct(Point2D point) {
        return new Vector2D(point.x - x, point.y - y);
    }

    public double abs(Point2D point) {
        double dx = point.x - x;
        double dy = point.y - y;
        return MathHelper.sqrt(dx * dx + dy * dy);
    }

    public Point2D minus(Point2D other) {
        return new Point2D(x - other.x, y - other.y);
    }

    public Point2D add(Point2D other) {
        return new Point2D(x + other.x, y + other.y);
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
        return this.equals(Zero);
    }

    public Vector2D toV2D() {
        return new Vector2D(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point2D) {
            Point2D v = (Point2D) obj;
            return v.x == x &&
                    v.y == y;
        }
        return false;
    }
}
