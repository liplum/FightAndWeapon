package net.liplum.lib.weapons;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public interface IHarpCore {
    /**
     * Gets the radius of the range
     *
     * @return the radius of the skill.
     */
    double getRadius();

    void releaseSkill(World world, EntityPlayer player, EnumHand handIn, EntityLivingBase target);

    /**
     * Gets the cool down time of weapon.
     *
     * @return The cool down time of weapon(by tick)
     */
    int getCoolDown();
}
