package net.liplum.attributes;

import net.liplum.api.weapon.WeaponCore;

public class HasNoSuchAttributeException extends RuntimeException {
    public HasNoSuchAttributeException(Attribute attribute) {
        super("Has No [" + attribute.getRegisterName() + "] Attribute");
    }

    public HasNoSuchAttributeException(Attribute attribute, WeaponCore weaponCore) {
        super("Has No [" + attribute.getRegisterName() + "] Attribute in " + weaponCore);
    }
}
