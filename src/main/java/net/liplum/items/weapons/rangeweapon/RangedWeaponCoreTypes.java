package net.liplum.items.weapons.rangeweapon;

import net.liplum.api.weapon.WeaponSkillArgs;

public class RangedWeaponCoreTypes {
    public static final RangedWeaponCore Sickle = new RangedWeaponCore() {
        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            return false;
        }
    };
}
