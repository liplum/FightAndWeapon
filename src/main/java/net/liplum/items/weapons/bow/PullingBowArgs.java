package net.liplum.items.weapons.bow;

import net.liplum.api.weapon.WeaponSkillArgs;

import javax.annotation.Nonnull;

public class PullingBowArgs extends WeaponSkillArgs {
    private int pullingTick;

    public int getPullingTick() {
        return pullingTick;
    }

    @Nonnull
    public PullingBowArgs setPullingTick(int pullingTick) {
        this.pullingTick = pullingTick;
        return this;
    }
}
