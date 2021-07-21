package net.liplum.lib.utils;

import net.liplum.I18ns;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponPart;
import net.liplum.api.weapon.WeaponType;

import javax.annotation.Nonnull;

public final class FawI18n {

    @Nonnull
    public static String getNameI18nKey(@Nonnull WeaponPart weaponPart) {
        return I18ns.endWithName(I18ns.prefixItem(weaponPart.getRegisterName()));
    }

    @Nonnull
    public static String getNameI18nKey(@Nonnull WeaponType weaponType) {
        return I18ns.prefixWeaponType(weaponType.getRegisterName());
    }

    @Nonnull
    public static String getNameI18nKey(@Nonnull IPassiveSkill<?> passiveSkill) {
        return I18ns.prefixPSkill(passiveSkill.getRegisterName());
    }

    @Nonnull
    public static String getDescriptionI18nKey(@Nonnull IPassiveSkill<?> passiveSkill) {
        return I18ns.endWithDescription(I18ns.prefixPSkill(passiveSkill.getRegisterName()));
    }

    @Nonnull
    public static String getNameI18nKey(@Nonnull IGemstone gemstone) {
        return I18ns.endWithName(I18ns.prefixItem(gemstone.getRegisterName()));
    }

    @Nonnull
    public static String getWeaponSkillTipI18nKey(@Nonnull WeaponCore weaponCore) {
        return I18ns.Tooltip.skill(
                weaponCore.getWeaponType().getRegisterName(),
                weaponCore.getRegisterName()
        );
    }

    @Nonnull
    public static String getGemstoneSkillTipI18nKey(@Nonnull WeaponCore weaponCore, @Nonnull IGemstone gemstone) {
        return I18ns.Tooltip.skill(
                weaponCore.getWeaponType().getRegisterName(),
                weaponCore.getRegisterName(),
                gemstone.getRegisterName()
        );
    }
}
