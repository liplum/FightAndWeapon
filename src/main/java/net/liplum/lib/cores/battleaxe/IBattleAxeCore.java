package net.liplum.lib.cores.battleaxe;

import net.liplum.api.weapon.IWeaponCore;
import net.liplum.items.weapons.battleaxe.BattleAxeItem;

public interface IBattleAxeCore extends IWeaponCore {
    boolean releaseSkill(BattleAxeArgs args);

    float getSweepRange();

    @Override
    default Class<BattleAxeItem> getWeaponType() {
        return BattleAxeItem.class;
    }
}
