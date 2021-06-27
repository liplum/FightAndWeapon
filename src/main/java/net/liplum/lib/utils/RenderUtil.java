package net.liplum.lib.utils;

import com.google.common.base.Predicates;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public final class RenderUtil {
    public static RayTraceResult extendReachRayTrace(double distance) {
        Minecraft mc = Minecraft.getMinecraft();
        Entity player = mc.getRenderViewEntity();

        if (player != null && player.world != null) {
            Vec3d lookVec = player.getLook(mc.getRenderPartialTicks());
            Vec3d eyesVec = player.getPositionEyes(mc.getRenderPartialTicks());
            Vec3d extendedEyesVec = eyesVec.addVector(lookVec.x * distance, lookVec.y * distance, lookVec.z * distance);
            List<Entity> collisionList = mc.world.getEntitiesInAABBexcluding(player, player.getEntityBoundingBox().expand(lookVec.x * distance, lookVec.y * distance, lookVec.z * distance).grow(0.5D, 0.5D, 0.5D), Predicates.and(EntitySelectors.NOT_SPECTATING, entity -> !entity.isDead));

            Entity closestTarget = null;
            double closestDistance = distance + 1;
            Vec3d closestHitVec = null;

            for (int i = collisionList.size() - 1; i >= 0; i--) {
                Entity collided = collisionList.get(i);
                AxisAlignedBB axis = collided.getEntityBoundingBox().grow(collided.getCollisionBorderSize());
                RayTraceResult collisionRay = axis.calculateIntercept(eyesVec, extendedEyesVec);

                if (axis.contains(eyesVec)) {
                    if (closestDistance > 0) {
                        closestTarget = collided;
                        closestHitVec = collisionRay == null ? eyesVec : collisionRay.hitVec;
                        closestDistance = 0;
                    }
                } else if (collisionRay != null) {
                    double collidedDistance = eyesVec.distanceTo(collisionRay.hitVec);

                    if (collidedDistance < closestDistance || closestDistance == 0) {
                        if (collided.getLowestRidingEntity() == player.getLowestRidingEntity() && !collided.canRiderInteract()) {
                            if (closestDistance == 0) {
                                closestTarget = collided;
                                closestHitVec = collisionRay.hitVec;
                            }
                        } else {
                            closestTarget = collided;
                            closestHitVec = collisionRay.hitVec;
                            closestDistance = collidedDistance;
                        }
                    }
                }
            }

            if (closestTarget != null && eyesVec.distanceTo(closestHitVec) <= distance && (mc.objectMouseOver == null || mc.objectMouseOver.typeOfHit != RayTraceResult.Type.BLOCK))
                return new RayTraceResult(closestTarget, closestHitVec);
        }

        return null;
    }
}
