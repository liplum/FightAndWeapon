package net.liplum.lib.weapons;

import java.util.List;

public interface IHarp extends ISkillableWeapon{
    /**
     * Gets the radius of the range
     * @return the radius of the skill.
     */
    double getRadius();

    /**
     * Sets the radius of the range
     * @param newRadius the radius of the skill.
     */
    void setRadius(double newRadius);

    /**
     * Gets all harp skills of this harp
     * @return all harp skills
     */
    List<IHarpSkill> getHarpSkills();
}
