package net.liplum.lib.weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public interface ISkillableWeapon {
    /**
     * Gets the cool down time of weapon.
     *
     * @return The cool down time of weapon(Unit: tick)
     */
    int getCoolDown();
}
