package net.liplum.eventhandlers;

import net.liplum.events.LanceSprintEvent;
import net.liplum.items.weapons.LanceItem;
import net.liplum.lib.math.Point;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.liplum.lib.math.MathTool.isInside;
import static net.liplum.lib.math.MathTool.genRectangleFrom;
import static net.liplum.lib.math.MathTool.toV2D;

import java.util.List;

import static net.liplum.lib.math.MathTool.*;

@Mod.EventBusSubscriber
public class LanceSprintHandler {

    @SubscribeEvent
    public static void onLanceSprint(LanceSprintEvent event) {
        EntityPlayer p = event.player;
        LanceItem l = event.lance;
        Vec3d v = event.sprintVec3d;
        double length = l.getSprintLength();
        AxisAlignedBB playerBox = p.getEntityBoundingBox();
        World world = p.world;
        Point pp = new Point(p.posX, p.posZ);
        List<EntityLivingBase> allInRange;
        allInRange = world
                .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(length, 0.25D, length));
        for (EntityLivingBase e : allInRange) {
            Point ep = new Point(e.posX, e.posZ);
            Point ep2pp = ep.minus(pp);//Change the global coordinate to player's local coordinate
            if (e != p &&
                    isInside(genRectangleFrom(toV2D(v), 2), ep2pp)) {
                if (e != p &&
                        isInside(genRectangleFrom(toV2D(v), 2), ep2pp)) {
                    e.attackEntityFrom(DamageSource.causePlayerDamage(p), 1
                            //getAttackDamage()+getDamage(stack)
                    );
                }
            }
        }
    }
}
