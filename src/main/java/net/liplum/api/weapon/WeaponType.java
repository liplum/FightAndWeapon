package net.liplum.api.weapon;

import javax.annotation.Nonnull;

public class WeaponType {
    @Nonnull
    private final String registerName;

    public WeaponType(@Nonnull String registerName) {
        this.registerName = registerName;
    }

    @Nonnull
    public String getRegisterName() {
        return registerName;
    }
}
