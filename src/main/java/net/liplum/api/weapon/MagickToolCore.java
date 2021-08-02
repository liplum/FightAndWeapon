package net.liplum.api.weapon;

import javax.annotation.Nonnull;

public abstract class MagickToolCore extends WeaponCore {
    public MagickToolCore(@Nonnull String registerName) {
        super(registerName);
    }

    public MagickToolCore(@Nonnull String registerName, boolean hasWeaponSkill) {
        super(registerName, hasWeaponSkill);
    }
}
