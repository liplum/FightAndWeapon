package net.liplum.items.weapons.battleaxe;

import net.liplum.api.weapon.WeaponCore;

import javax.annotation.Nonnull;

public abstract class BattleAxeCore extends WeaponCore {
    public abstract boolean releaseSkill(BattleAxeArgs args);

/*    public abstract float getSweepRange();*/

    @Nonnull
    @Override
    public Class<BattleAxeItem> getWeaponType() {
        return BattleAxeItem.class;
    }
}
