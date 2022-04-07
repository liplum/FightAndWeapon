package net.liplum.api.weapon;

import org.jetbrains.annotations.NotNull;

public class Cast {
    private final WeaponPart weaponPart;

    public Cast(@NotNull WeaponPart weaponPart) {
        this.weaponPart = weaponPart;
    }

    @NotNull
    public String getRegisterName() {
        return weaponPart.getRegisterName();
    }

    @NotNull
    public WeaponPart getWeaponPart() {
        return weaponPart;
    }

    public int getID() {
        return weaponPart.getID();
    }
}
