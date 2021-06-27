package net.liplum.api.weapon;

import net.liplum.AttributeDefault;

public interface IMagicToolCore extends IWeaponCore {
    default float getAbilityPower() {
        return AttributeDefault.Generic.AbilityPower;
    }
}
