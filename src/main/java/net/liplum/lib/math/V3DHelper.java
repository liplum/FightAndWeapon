package net.liplum.lib.math;

import net.minecraft.util.math.Vec3d;

public final class V3DHelper {

    public Vec3d minus(Vec3d a,Vec3d b) {
        return a.addVector(-b.x,-b.y,-b.z);
    }

    public Vec3d reserve(Vec3d v3d){
        return new Vec3d(-v3d.x, -v3d.y ,-v3d.z);
    }

    public Vec3d divide(Vec3d v3d,double division) {
        return new Vec3d(v3d.x / division, v3d.y / division, v3d.z / division);
    }

    public static Vec3d from2D(Vector2D v2d) {
        return new Vec3d(v2d.x, v2d.y, 0);
    }
}
