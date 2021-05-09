package net.liplum.lib.items;

import net.liplum.lib.modifiers.IHarpModifier;
import net.liplum.lib.weaponcores.IHarpCore;

public interface IHarp extends ISkillableWeapon{
    /**
     * Gets the core.
     * @return A core of the harp.
     */
    IHarpCore getHarpCore();
    /**
     * Gets the modifier.
     * @return A modifier of the harp.
     */
    IHarpModifier getHarpModifier();
    /**
     * Sets the modifier.
     * @return A modifier of the harp.
     */
    void setHarpModifier(IHarpModifier modifier);
    /**
     * Gets all harp skills of this harp
     * @return all harp skills
     */
    //List<IHarpSkill> getHarpSkills();
}
