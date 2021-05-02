package net.liplum.lib.weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IHarp extends ISkillableWeapon{
    /**
     *
     * @return the radius of the skill.
     */
    double getRadius();
    void setRadius(double newRadius);
}
