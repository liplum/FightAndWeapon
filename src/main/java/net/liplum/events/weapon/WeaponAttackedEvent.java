package net.liplum.events.weapon;

import javax.annotation.Nonnull;

public class WeaponAttackedEvent extends WeaponAttackBaseEvent<WeaponAttackedArgs> {

    public WeaponAttackedEvent(WeaponAttackedArgs args) {
        super(args);
    }

    @Nonnull
    @Override
    public WeaponAttackedArgs getArgs() {
        return (WeaponAttackedArgs) args;
    }
}
