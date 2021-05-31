package net.liplum.api.fight;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class SkillArgs<This extends SkillArgs> {
    private World world = null;
    private EntityPlayer player = null;

    public World getWorld() {
        return world;
    }

    public This setWorld(World world) {
        this.world = world;
        return (This) this;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public This setPlayer(EntityPlayer player) {
        this.player = player;
        return (This) this;
    }
}
