package net.liplum.api.fight;

import javax.annotation.Nonnull;

public interface IPSkillCoolingTimer {
    void addNewCoolDown(@Nonnull IPassiveSkill<?> passiveSkill, int coolDownTicks);

    boolean isInCoolingDown(@Nonnull IPassiveSkill<?> passiveSkill);

    void tick();

    default boolean isNotInCoolingDown(@Nonnull IPassiveSkill<?> passiveSkill){
        return !isInCoolingDown(passiveSkill);
    }
}
