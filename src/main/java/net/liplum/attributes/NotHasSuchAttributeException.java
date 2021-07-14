package net.liplum.attributes;

import net.liplum.api.weapon.WeaponCore;

public class NotHasSuchAttributeException extends RuntimeException {
    public NotHasSuchAttributeException(IAttribute attribute) {
        super("Has No [" + attribute.getRegisterName() + "] Attribute");
    }

    public NotHasSuchAttributeException(IAttribute attribute, WeaponCore weaponCore) {
        super("Has No [" + attribute.getRegisterName() + "] Attribute in " + weaponCore);
    }
}
