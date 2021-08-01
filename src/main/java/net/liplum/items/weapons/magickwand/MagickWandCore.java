package net.liplum.items.weapons.magickwand;

import net.liplum.WeaponTypes;
import net.liplum.api.weapon.MagickToolCore;
import net.liplum.api.weapon.WeaponType;

import javax.annotation.Nonnull;

abstract class MagickWandCore extends MagickToolCore {

    public MagickWandCore(@Nonnull String registerName) {
        super(registerName);
    }

    @Override
    protected void build(@Nonnull WeaponCoreBuilder builder) {
        super.build(builder);
    }

    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.MagickWand;
    }
}
