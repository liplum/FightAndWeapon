package net.liplum.api.weapon;

public class Cast {
    private final WeaponPart weaponPart;

    public Cast(WeaponPart weaponPart) {
        this.weaponPart = weaponPart;
    }

    public String getRegisterName() {
        return weaponPart.getRegisterName();
    }

    public WeaponPart getWeaponPart() {
        return weaponPart;
    }

    public int getID() {
        return weaponPart.getID();
    }
}
