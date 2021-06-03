package net.liplum.lib.cores.battleaxe;

import net.liplum.api.weapon.WeaponArgs;

public class BattleAxeArgs extends WeaponArgs<BattleAxeArgs> {
    private float sweepRange = 0;

    public BattleAxeArgs() {
    }

    public float getSweepRange() {
        return sweepRange;
    }

    public BattleAxeArgs setSweepRange(float sweepRange) {
        this.sweepRange = sweepRange;
        return this;
    }
}
