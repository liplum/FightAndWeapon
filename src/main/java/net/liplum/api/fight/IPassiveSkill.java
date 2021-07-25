package net.liplum.api.fight;

import net.liplum.api.annotations.Require;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;

public interface IPassiveSkill<EventType extends Event> {
    @Nonnull
    IEventTypeArgs getEventTypeArgs();

    @Nonnull
    PSkillResult onTrigger(@Nonnull EventType event);

    @Nonnull
    String getRegisterName();

    default boolean isShownInTooltip() {
        return true;
    }

    /**
     * Smaller number means front<br/>
     * Larger number means later<br/>
     * O is default.
     */
    default int getTriggerPriority() {
        return 0;
    }

    boolean isBanedWhenBroken();

    boolean hasCoolDown();

    @Require(func = "hasCoolDown", is = "true")
    int getCoolDownTicks();
}
