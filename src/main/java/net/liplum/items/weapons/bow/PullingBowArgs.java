package net.liplum.items.weapons.bow;

import net.liplum.api.weapon.WeaponSkillArgs;
import org.jetbrains.annotations.NotNull;

public class PullingBowArgs extends WeaponSkillArgs {
    private int pullingTick;

    public int getPullingTick() {
        return pullingTick;
    }

    @NotNull
    public PullingBowArgs setPullingTick(int pullingTick) {
        this.pullingTick = pullingTick;
        return this;
    }
}
