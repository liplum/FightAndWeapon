package net.liplum.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;


public class PlayerCollisionEvent extends Event {
    public final EntityPlayer player;
    public final Entity[] collided;

    public PlayerCollisionEvent(@Nonnull EntityPlayer player, @Nonnull Entity... entities) {
        this.collided = entities;
        this.player = player;
    }
}
