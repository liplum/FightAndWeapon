package net.liplum.lib.modifiers;

import net.liplum.api.weapon.IModifier;
import net.liplum.lib.cores.battleaxe.BattleAxeArgs;
import net.liplum.lib.cores.battleaxe.IBattleAxeCore;

public abstract class BattleAxeIModifier implements IModifier<IBattleAxeCore> {
    public boolean releaseSkill(IBattleAxeCore core, BattleAxeArgs args) {
        return core.releaseSkill(args);
    }

    public float getSweepRangeDelta() {
        return 0;
    }

    public float getSweepRate() {
        return 0;
    }
}
