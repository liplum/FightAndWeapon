package net.liplum.lib.items.gemstone;

import net.liplum.lib.modifiers.Modifier;
import net.liplum.lib.weaponcores.IWeaponCore;

import javax.annotation.Nullable;

public interface IGemstone {

    boolean hasModifierOf(IWeaponCore core);

    /**
     * @param core
     * @return the modifier or null if it didn't has a corresponding modifier of the core in this gemstone.
     */
    @Nullable
    Modifier getModifierOf(IWeaponCore core);

    Gemstone addModifier(Modifier newModifier);

    String getRegisterName();
}
