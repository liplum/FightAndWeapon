package net.liplum.lib.tools;

import net.liplum.lib.math.Vector2D;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;

public class PhysicsEngine {
    /**
     * Sets the direction of movement
     * @param e the entity
     * @param v3d the direction
     */
    public static void setMotion(LivingEntity e, Vector3d v3d) {
        e.setMotion(v3d);
    }

    /**
     * Sets the direction of movement
     * @param e the entity
     * @param x the X-axis of direction
     * @param y the Y-axis of direction
     * @param z the Z-axis of direction
     */
    public static void setMotion(LivingEntity e, double x, double y, double z) {
        e.setMotion(x, y, z);
    }

    /**
     * Sets the direction of movement without Y-axis.
     * @param e the entity
     * @param v2d the direction without Y-axis.
     */
    public static void setMotion(LivingEntity e, Vector2D v2d) {
        e.setMotion(v2d.x, 0, v2d.y);
    }
}
