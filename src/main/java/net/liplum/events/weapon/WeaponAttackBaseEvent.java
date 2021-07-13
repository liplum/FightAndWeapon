package net.liplum.events.weapon;

import net.liplum.api.weapon.WeaponAttackArgs;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;

public abstract class WeaponAttackBaseEvent<Args extends WeaponAttackArgs<?>> extends Event {
    protected final WeaponAttackArgs<Args> args;

    public WeaponAttackBaseEvent(WeaponAttackArgs<Args> args) {
        this.args = args;
    }

    @Nonnull
    public abstract WeaponAttackArgs<Args> getArgs();
}
