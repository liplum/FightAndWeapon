package net.liplum.lib.weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public interface IHarpSkill {
    void releaseHarpSkill(World world, EntityPlayer player, EnumHand handIn);
}
