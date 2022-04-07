package net.liplum.api.weapon;

import org.jetbrains.annotations.NotNull;

public abstract class MagickToolCore extends WeaponCore {
    public MagickToolCore(@NotNull String registerName) {
        super(registerName);
    }

    public MagickToolCore(@NotNull String registerName, boolean hasWeaponSkill) {
        super(registerName, hasWeaponSkill);
    }
}
