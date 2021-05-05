package net.liplum.lib.math;

import com.sun.javafx.geom.Vec3d;
import net.minecraft.util.math.vector.Vector3d;

public final class V3DHelper {

    public Vector3d reserve(Vector3d v3d){
        return new Vector3d(-v3d.x, -v3d.y ,-v3d.z);
    }

    public Vector3d divide(Vector3d v3d,double division) {
        return new Vector3d(v3d.x / division, v3d.y / division, v3d.z / division);
    }

    public static Vector3d from2D(Vector2D v2d) {
        return new Vector3d(v2d.x, v2d.y, 0);
    }

    public static Point to2DPoint(Vector3d v3d){
        return new Point(v3d.x,v3d.z);
    }
}
