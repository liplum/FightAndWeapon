package net.liplum.events.weapon;

import javax.annotation.Nonnull;

public class WeaponAttackingEvent extends WeaponAttackBaseEvent<WeaponAttackingArgs> {

    public WeaponAttackingEvent(WeaponAttackingArgs args) {
        super(args);
    }

    @Nonnull
    @Override
    public WeaponAttackingArgs getArgs() {
        return (WeaponAttackingArgs) args;
    }
}
