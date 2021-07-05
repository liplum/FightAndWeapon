package net.liplum.items.weapons.battleaxe;

import net.liplum.api.weapon.IModifier;

public abstract class BattleAxeModifier implements IModifier<BattleAxeCore> {
    public boolean releaseSkill(BattleAxeCore core, BattleAxeArgs args) {
        return core.releaseSkill(args);
    }

    public float getSweepRangeDelta() {
        return 0;
    }

    public float getSweepRate() {
        return 0;
    }
}
