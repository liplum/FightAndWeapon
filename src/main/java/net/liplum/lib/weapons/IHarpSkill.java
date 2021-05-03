package net.liplum.lib.weapons;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public interface IHarpSkill {
    void releaseHarpSkill(World world, PlayerEntity player, Hand handIn);
}
