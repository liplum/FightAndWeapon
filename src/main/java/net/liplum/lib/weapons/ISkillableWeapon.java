package net.liplum.lib.weapons;

public interface ISkillableWeapon {
    /**
     * Gets the cool down time of weapon.
     * @return The cool down time of weapon(by tick)
     */
    int getCoolDown();

    /**
     * Sets the cool down time of weapon
     * @param tick cool down time
     */
    void setCoolDown(int tick);
}
