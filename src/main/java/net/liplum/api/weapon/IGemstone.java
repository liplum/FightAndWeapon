package net.liplum.api.weapon;

import net.liplum.api.fight.IPassiveSkill;

import javax.annotation.Nullable;

public interface IGemstone {

    boolean hasModifierOf(IWeaponCore core);

    /**
     * @param core
     * @return the modifier or null if it didn't has a corresponding modifier of the core in this gemstone.
     */
    @Nullable
    IModifier getModifierOf(IWeaponCore core);

    IGemstone addModifier(IModifier newModifier);

    IGemstone addPassiveSkill(IPassiveSkill newPassiveSkill);

    String getRegisterName();
}
