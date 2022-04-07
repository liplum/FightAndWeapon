package net.liplum.items.weapons.magickwand;

import net.liplum.WeaponTypes;
import net.liplum.api.weapon.MagickToolCore;
import net.liplum.api.weapon.WeaponType;
import org.jetbrains.annotations.NotNull;

abstract class MagickWandCore extends MagickToolCore {

    public MagickWandCore(@NotNull String registerName) {
        super(registerName);
    }

    public MagickWandCore(@NotNull String registerName, boolean hasWeaponSkill) {
        super(registerName, hasWeaponSkill);
    }

    @Override
    protected void build(@NotNull WeaponCoreBuilder builder) {
        super.build(builder);
    }

    @NotNull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.MagickWand;
    }
}
