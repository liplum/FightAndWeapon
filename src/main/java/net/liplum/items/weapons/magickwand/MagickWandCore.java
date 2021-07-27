package net.liplum.items.weapons.magickwand;

import net.liplum.WeaponTypes;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponType;

import javax.annotation.Nonnull;

public abstract class MagickWandCore extends WeaponCore {

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
