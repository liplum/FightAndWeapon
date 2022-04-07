package net.liplum.api.fight;

import net.liplum.api.annotations.Developing;
import net.liplum.api.annotations.LongSupport;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Map;

@LongSupport
public interface IPSkillCoolingTimer {
    @LongSupport
    void addNewCoolDown(@NotNull IPassiveSkill<?> passiveSkill, int coolDownTicks);

    @LongSupport
    boolean isInCoolingDown(@NotNull IPassiveSkill<?> passiveSkill);

    @LongSupport
    void tick();

    @LongSupport
    default boolean isNotInCoolingDown(@NotNull IPassiveSkill<?> passiveSkill) {
        return !isInCoolingDown(passiveSkill);
    }

    @Nullable
    @Developing
    Map<IPassiveSkill<?>, CoolDown> getCoolingPassiveSkills();
}
