package net.liplum.api.weapon;

import javax.annotation.Nonnull;

public class Cast {
    private final WeaponPart weaponPart;

    public Cast(@Nonnull WeaponPart weaponPart) {
        this.weaponPart = weaponPart;
    }

    @Nonnull
    public String getRegisterName() {
        return weaponPart.getRegisterName();
    }

    @Nonnull
    public WeaponPart getWeaponPart() {
        return weaponPart;
    }

    public int getID() {
        return weaponPart.getID();
    }
}
