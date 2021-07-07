package net.liplum.api.fight;

import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;

public interface IPassiveSkill<EventType extends Event> extends ISkill {
    @Nonnull
    Class<EventType> getEventType();

    @Nonnull
    PSkillResult onTrigger(@Nonnull EventType event);

    @Nonnull
    String getRegisterName();

    default boolean isShownInTooltip() {
        return true;
    }
}
