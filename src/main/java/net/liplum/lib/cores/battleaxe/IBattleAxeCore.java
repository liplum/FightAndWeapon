package net.liplum.lib.cores.battleaxe;

import net.liplum.api.weapon.IWeaponCore;

public interface IBattleAxeCore extends IWeaponCore {
    boolean releaseSkill(BattleAxeArgs args);

    float getSweepRange();
}
