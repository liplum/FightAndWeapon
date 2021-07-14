package net.liplum.api.fight;

import net.liplum.api.registeies.SkillRegistry;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public abstract class PassiveSkill<EventType extends Event> implements IPassiveSkill<EventType> {
    @Nonnull
    private final String registerName;
    @Nonnull
    private final EventTypeArgs eventTypeArgs;
    private boolean isShownInTooltip = true;

    /**
     * Whenever you create the instance, it will register itself to {@link SkillRegistry} automatically.
     *
     * @param registerName the name to register itself
     * @param eventTypeClz the class of the event which you want to subscribe
     */
    public PassiveSkill(@Nonnull String registerName, Class<EventType> eventTypeClz) {
        this.registerName = registerName;
        this.eventTypeArgs = new EventTypeArgs((Class<Event>) eventTypeClz);
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

    public static class EventTypeArgs implements IEventTypeArgs {
        @Nonnull
        private final Class<Event> clz;
        @Nonnull
        private final LinkedList<Class<Event>> eventType;

        public EventTypeArgs(@Nonnull Class<Event> clz) {
            this.clz = clz;
            this.eventType = new LinkedList<>();
            this.eventType.add(clz);
        }

        @Override
        public boolean has(Class<Event> eventType) {
            return clz == eventType;
        }

        @Override
        public List<Class<Event>> getAllEventTypes() {
            return eventType;
        }

    }
}
