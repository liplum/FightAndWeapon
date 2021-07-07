package net.liplum.lib.utils;

import net.liplum.I18ns;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.WeaponPart;
import net.liplum.api.weapon.WeaponType;

import javax.annotation.Nonnull;

public final class FawI18n {

    @Nonnull
    public static String getNameI18nKey(WeaponPart weaponPart) {
        return I18ns.endWithName(I18ns.prefixItem(weaponPart.getRegisterName()));
    }

    @Nonnull
    public static String getNameI18nKey(WeaponType weaponType) {
        return I18ns.prefixWeaponType(weaponType.getRegisterName());
    }

    public static String getNameI18nKey(IPassiveSkill<?> passiveSkill) {
        return I18ns.prefixPSkill(passiveSkill.getRegisterName());
    }

    public static String getDescriptionI18nKey(IPassiveSkill<?> passiveSkill) {
        return I18ns.endWithDescription(I18ns.prefixPSkill(passiveSkill.getRegisterName()));
    }

    public static String getNameI18nKey(IGemstone gemstone) {
        return I18ns.endWithName(I18ns.prefixItem(gemstone.getRegisterName()));
    }
}
