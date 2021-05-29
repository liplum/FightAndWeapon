package net.liplum.lib.weaponcores;

public interface IWeaponCore {
    /**
     * Gets the cool down time of weapon.
     *
     * @return The cool down time of weapon(by tick)
     */
    int getCoolDown();

    default float getStrength() {
        return 0;
    }

    default int getEnemyBreakingTime() {
        return -1;
    }

    default float getKnockbackStrength() {
        return -1;
    }
}
