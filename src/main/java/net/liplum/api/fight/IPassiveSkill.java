package net.liplum.api.fight;

import net.liplum.api.annotations.Require;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.jetbrains.annotations.NotNull;

public interface IPassiveSkill<EventType extends Event> {
    @NotNull
    IEventTypeArgs getEventTypeArgs();

    @NotNull
    PSkillResult onTrigger(@NotNull EventType event);

    @NotNull
    String getRegisterName();

    default boolean isShownInTooltip() {
        return true;
    }

    /**
     * Smaller number means front<br/>
     * Larger number means later<br/>
     * O is default.
     */
    @Require(func = "isShownInTooltip", is = "true")
    default int getTriggerPriority() {
        return 0;
    }

    boolean isBanedWhenBroken();

    boolean hasCoolDown();

    @Require(func = "hasCoolDown", is = "true")
    int getCoolDownTicks();
}
