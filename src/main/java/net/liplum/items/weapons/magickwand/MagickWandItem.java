package net.liplum.items.weapons.magickwand;

import net.liplum.api.weapon.WeaponBaseItem;

import javax.annotation.Nonnull;

public class MagickWandItem extends WeaponBaseItem {
    @Nonnull
    private final MagickWandCore core;

    public MagickWandItem(@Nonnull MagickWandCore weaponCore) {
        super(weaponCore);
        this.core = weaponCore;
    }

    @Nonnull
    @Override
    public MagickWandCore getConcreteCore() {
        return core;
    }

}
