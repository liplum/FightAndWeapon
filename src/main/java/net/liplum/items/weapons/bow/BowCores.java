package net.liplum.items.weapons.bow;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.WeaponSkillArgs;

@LongSupport
public final class BowCores {
    public static final BowCore Empty = new BowCore() {
        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            return false;
        }
    };

}
