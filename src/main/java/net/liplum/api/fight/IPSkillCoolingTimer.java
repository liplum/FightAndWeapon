package net.liplum.api.fight;

import net.liplum.api.annotations.Developing;
import net.liplum.api.annotations.LongSupport;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

@LongSupport
public interface IPSkillCoolingTimer {
    @LongSupport
    void addNewCoolDown(@Nonnull IPassiveSkill<?> passiveSkill, int coolDownTicks);

    @LongSupport
    boolean isInCoolingDown(@Nonnull IPassiveSkill<?> passiveSkill);

    @LongSupport
    void tick();

    @LongSupport
    default boolean isNotInCoolingDown(@Nonnull IPassiveSkill<?> passiveSkill) {
        return !isInCoolingDown(passiveSkill);
    }

    @Nullable
    @Developing
    Map<IPassiveSkill<?>, CoolDown> getCoolingPassiveSkills();
}
