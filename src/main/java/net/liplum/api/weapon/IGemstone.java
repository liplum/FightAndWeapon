package net.liplum.api.weapon;

import net.liplum.api.fight.IPassiveSkill;

import javax.annotation.Nullable;
import java.awt.*;

public interface IGemstone {

    boolean hasModifierOf(IWeaponCore core);

    /**
     * @param core
     * @return the modifier or null if it didn't has a corresponding modifier of the core in this gemstone.
     */
    @Nullable
    IModifier getModifierOf(IWeaponCore core);

    @Nullable
    IPassiveSkill<?>[] getPassiveSkillsOf(IWeaponCore core);

    IGemstone addModifier(IModifier newModifier);

    IGemstone addPassiveSkill(IWeaponCore core,IPassiveSkill newPassiveSkill);

    String getRegisterName();
}
