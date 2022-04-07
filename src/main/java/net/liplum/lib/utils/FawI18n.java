package net.liplum.lib.utils;

import net.liplum.I18ns;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponPart;
import net.liplum.api.weapon.WeaponType;
import org.jetbrains.annotations.NotNull;

@LongSupport
public final class FawI18n {

    @NotNull
    @LongSupport
    public static String getNameI18nKey(@NotNull WeaponPart weaponPart) {
        return I18ns.endWithName(I18ns.prefixItem(weaponPart.getRegisterName()));
    }

    @NotNull
    @LongSupport
    public static String getNameI18nKey(@NotNull WeaponType weaponType) {
        return I18ns.prefixWeaponType(weaponType.getRegisterName());
    }

    @NotNull
    @LongSupport
    public static String getNameI18nKey(@NotNull IPassiveSkill<?> passiveSkill) {
        return I18ns.prefixPSkill(passiveSkill.getRegisterName());
    }

    @NotNull
    @LongSupport
    public static String getDescriptionI18nKey(@NotNull IPassiveSkill<?> passiveSkill) {
        return I18ns.endWithDescription(I18ns.prefixPSkill(passiveSkill.getRegisterName()));
    }

    @NotNull
    @LongSupport
    public static String getNameI18nKey(@NotNull IGemstone gemstone) {
        return I18ns.endWithName(I18ns.prefixItem(gemstone.getRegisterName()));
    }

    @NotNull
    @LongSupport
    public static String getWeaponSkillTipI18nKey(@NotNull WeaponCore weaponCore) {
        return I18ns.Tooltip.skill(
                weaponCore.getWeaponType().getRegisterName(),
                weaponCore.getRegisterName()
        );
    }

    @NotNull
    @LongSupport
    public static String getGemstoneSkillTipI18nKey(@NotNull WeaponCore weaponCore, @NotNull IGemstone gemstone) {
        return I18ns.Tooltip.skill(
                weaponCore.getWeaponType().getRegisterName(),
                weaponCore.getRegisterName(),
                gemstone.getRegisterName()
        );
    }
}
