package net.liplum.lib.items;

import net.liplum.lib.weaponcores.ILanceCore;

public interface ILance extends ISkillableWeapon{
    /**
     * Gets the core.
     * @return A core of the harp.
     */
    ILanceCore getLanceCore();
}
