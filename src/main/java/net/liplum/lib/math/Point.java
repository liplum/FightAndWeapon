package net.liplum.lib.math;

import net.minecraft.util.math.MathHelper;

public class Point {
    public final double x;
    public final double y;

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

    public Point minus(Point other){
        return new Point(x-other.x,y-other.y);
    }
    public Vector2D toV2D(){
        return new Vector2D(x,y);
    }
}
