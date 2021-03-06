package net.liplum.api.fight;

import net.liplum.api.annotations.Require;
import net.liplum.api.registeies.SkillRegistry;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public abstract class PassiveSkill<EventType extends Event> implements IPassiveSkill<EventType> {
    @NotNull
    private final String registerName;
    @NotNull
    private final EventTypeArgs eventTypeArgs;
    private final boolean hasCoolDown;
    private final int coolDownTicks;
    private boolean isShownInTooltip = true;
    private int triggerPriority = 0;
    private boolean isBannedWhenBroken = true;

    /**
     * Whenever you create the instance, it will register itself to {@link SkillRegistry} automatically.
     *
     * @param registerName the name to register itself
     * @param eventTypeClz the class of the event which you want to subscribe
     */
    public PassiveSkill(@NotNull String registerName, Class<EventType> eventTypeClz, int coolDownTicks) {
        this.registerName = registerName;
        this.eventTypeArgs = new EventTypeArgs((Class<Event>) eventTypeClz);
        SkillRegistry.register(this);
        this.hasCoolDown = coolDownTicks > 0;
        this.coolDownTicks = hasCoolDown ? coolDownTicks : 0;
    }

    public PassiveSkill(@NotNull String registerName, Class<EventType> eventTypeClz) {
        this(registerName, eventTypeClz, 0);
    }


    @Override
    public int getTriggerPriority() {
        return triggerPriority;
    }

    @NotNull
    public PassiveSkill<EventType> setTriggerPriority(int triggerPriority) {
        this.triggerPriority = triggerPriority;
        return this;
    }

    @Override
    public boolean isBanedWhenBroken() {
        return isBannedWhenBroken;
    }

    @NotNull
    public PassiveSkill<EventType> setBanedWhenBroken(boolean banedWhenBroken) {
        this.isBannedWhenBroken = banedWhenBroken;
        return this;
    }

    @NotNull
    @Override
    public IEventTypeArgs getEventTypeArgs() {
        return eventTypeArgs;
    }

    @NotNull
    @Override
    public String getRegisterName() {
        return registerName;
    }

    @Override
    public boolean isShownInTooltip() {
        return isShownInTooltip;
    }

    @NotNull
    public PassiveSkill<EventType> setShownInTooltip(boolean isShownInTooltip) {
        this.isShownInTooltip = isShownInTooltip;
        return this;
    }

    @Override
    public boolean hasCoolDown() {
        return hasCoolDown;
    }

    @Override
    @Require(func = "hasCoolDown", is = "true")
    public int getCoolDownTicks() {
        return coolDownTicks;
    }

    public static class EventTypeArgs implements IEventTypeArgs {
        @NotNull
        private final Class<Event> clz;
        @NotNull
        private final LinkedList<Class<Event>> eventType;

        public EventTypeArgs(@NotNull Class<Event> clz) {
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
