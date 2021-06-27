package net.liplum.lib.cores.battleaxe;

import net.liplum.api.weapon.IWeaponCore;
import net.liplum.items.weapons.battleaxe.BattleAxeItem;

import javax.annotation.Nonnull;

public interface IBattleAxeCore extends IWeaponCore {
    boolean releaseSkill(BattleAxeArgs args);

    float getSweepRange();

    @Nonnull
    @Override
    default Class<BattleAxeItem> getWeaponType() {
        return BattleAxeItem.class;
    }
}
