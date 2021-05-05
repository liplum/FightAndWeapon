package net.liplum.lib.tools;

import net.liplum.lib.math.Vector2D;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

public class PhysicsEngine {
    /**
     * Sets the direction of movement
     * @param e the entity
     * @param v3d the direction
     */
    public static void setMotion(EntityLivingBase e, Vec3d v3d) {
        e.motionX= v3d.x;
        e.motionY= v3d.y;
        e.motionZ = v3d.z;
    }

    /**
     * Sets the direction of movement
     * @param e the entity
     * @param x the X-axis of direction
     * @param y the Y-axis of direction
     * @param z the Z-axis of direction
     */
    public static void setMotion(EntityLivingBase e, double x, double y, double z) {
        e.motionX= x;
        e.motionY= y;
        e.motionZ = z;
    }

    /**
     * Sets the direction of movement without Y-axis.
     * @param e the entity
     * @param v2d the direction without Y-axis.
     */
    public static void setMotion(EntityLivingBase e, Vector2D v2d) {
        setMotion(e,v2d.x,0.0D,v2d.y);
    }
}
