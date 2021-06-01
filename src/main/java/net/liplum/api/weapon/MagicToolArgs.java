package net.liplum.api.weapon;

import javax.annotation.Nonnull;

public class MagicToolArgs<This extends MagicToolArgs> extends WeaponArgs<This> {
    private float abilityPower = 0;

    public MagicToolArgs() {
    }

    @Nonnull
    public float getAbilityPower() {
        return abilityPower;
    }

    @Nonnull
    public This setAbilityPower(float abilityPower) {
        this.abilityPower = abilityPower;
        return (This) this;
    }
}
