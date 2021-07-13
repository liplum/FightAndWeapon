package net.liplum.attributes;

import net.liplum.api.weapon.WeaponCore;

public class NotHasSuchAttributeException extends RuntimeException {
    public NotHasSuchAttributeException(Attribute attribute) {
        super("Has No [" + attribute.getRegisterName() + "] Attribute");
    }

    public NotHasSuchAttributeException(Attribute attribute, WeaponCore weaponCore) {
        super("Has No [" + attribute.getRegisterName() + "] Attribute in " + weaponCore);
    }
}
