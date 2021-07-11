package net.liplum.api.fight;

import net.liplum.api.registeies.SkillRegistry;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;

public abstract class PassiveSkill<EventType extends Event> implements IPassiveSkill<EventType> {
    private boolean isShownInTooltip = true;
    @Nonnull
    private final String registerName;

    @Nonnull
    private final EventTypeArgs eventTypeArgs;

    /**
     * Whenever you create the instance, it will register itself to {@link SkillRegistry} automatically.
     * @param registerName the name to register itself
     * @param eventTypeClz the class of the event which you want to subscribe
     */
    public PassiveSkill(@Nonnull String registerName, Class<EventType> eventTypeClz) {
        this.registerName = registerName;
        this.eventTypeArgs = new EventTypeArgs(eventTypeClz);
        SkillRegistry.register(this);
    }

    @Nonnull
    @Override
    public IEventTypeArgs getEventTypeArgs() {
        return eventTypeArgs;
    }

    @Nonnull
    @Override
    public String getRegisterName() {
        return registerName;
    }

    @Override
    public boolean isShownInTooltip() {
        return isShownInTooltip;
    }

    public PassiveSkill<EventType> setShownInTooltip() {
        this.isShownInTooltip = true;
        return this;
    }
}
