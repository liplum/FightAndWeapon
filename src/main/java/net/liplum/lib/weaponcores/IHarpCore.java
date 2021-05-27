package net.liplum.lib.weaponcores;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public interface IHarpCore extends IWeaponCore {
    /**
     * Gets the radius of the range
     *
     * @return the radius of the skill.
     */
    double getRadius();

    void releaseHarpSkill(World world, EntityPlayer player, EnumHand handIn, EntityLivingBase target);
}
