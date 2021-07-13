package net.liplum.events.weapon;

import net.liplum.api.weapon.WeaponAttackArgs;

import javax.annotation.Nonnull;

public class WeaponAttackedArgs extends WeaponAttackArgs<WeaponAttackedArgs> {
    private boolean hitSuccessfully;
    private float totalDamage;

    public boolean isHitSuccessfully() {
        return hitSuccessfully;
    }

    public WeaponAttackedArgs setHitSuccessfully(boolean hitSuccessfully) {
        this.hitSuccessfully = hitSuccessfully;
        return this;
    }

    public float getTotalDamage() {
        return totalDamage;
    }

    @Nonnull
    public WeaponAttackedArgs setTotalDamage(float totalDamage) {
        this.totalDamage = totalDamage;
        return this;
    }
}
