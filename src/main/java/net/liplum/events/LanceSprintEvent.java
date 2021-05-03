package net.liplum.events;

import net.liplum.items.weapons.LanceItem;
import net.liplum.lib.math.MathTool;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.Event;


public class LanceSprintEvent extends Event {
    public final EntityPlayer player;
    public final LanceItem lance;
    public final Vec3d sprintVec3d;

    public LanceSprintEvent(EntityPlayer player, LanceItem lance, Vec3d sprintVec3d) {
        this.player = player;
        this.lance = lance;
        this.sprintVec3d = sprintVec3d;
    }
}
