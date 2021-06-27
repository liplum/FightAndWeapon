package net.liplum.api.weapon;

import net.liplum.AttributeDefault;
import net.liplum.lib.items.WeaponBaseItem;

import javax.annotation.Nonnull;

public interface IWeaponCore {
    /**
     * Gets the cool down time of weapon.
     *
     * @return The cool down time of weapon(by tick)
     */
    int getCoolDown();

    default float getStrength() {
        return AttributeDefault.Generic.Strength;
    }

    default double getAttackReach() {
        return AttributeDefault.Generic.AttackReach;
    }

    /**
     * Gets the upcoming invincible time of a enemy who was attacked by this weapon.
     *
     * @return the invincible time(by tick)
     */
    default int getEnemyBreakingTime() {
        return AttributeDefault.Generic.EnemyBreakingTime;
    }

    /**
     * Gets the knock back.the basic is 1.0F.
     *
     * @return the strength of knock back
     */
    default float getKnockbackStrength() {
        return AttributeDefault.Generic.KnockbackStrength;
    }

    @Nonnull
    Class<? extends WeaponBaseItem<?>> getWeaponType();
}
