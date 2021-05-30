package net.liplum.api.weapon;

import net.liplum.lib.items.gemstone.Gemstone;

import javax.annotation.Nullable;

public interface IGemstone {

    boolean hasModifierOf(IWeaponCore core);

    /**
     * @param core
     * @return the modifier or null if it didn't has a corresponding modifier of the core in this gemstone.
     */
    @Nullable
    IModifier getModifierOf(IWeaponCore core);

    Gemstone addModifier(IModifier newModifier);

    String getRegisterName();
}
