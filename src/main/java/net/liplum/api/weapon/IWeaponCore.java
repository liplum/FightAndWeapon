package net.liplum.api.weapon;

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

    /**
     * Gets the upcoming invincible time of a enemy who was attacked by this weapon.
     *
     * @return the invincible time(by tick)
     */
    default int getEnemyBreakingTime() {
        return 20;
    }

    /**
     * Gets the knock back.the basic is 1.0F.
     * @return the strength of knock back
     */
    default float getKnockbackStrength() {
        return 1;
    }
}
