package net.liplum.api.fight;

import net.liplum.api.annotations.Require;
import net.liplum.api.registeies.SkillRegistry;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class AggregatePassiveSkill implements IPassiveSkill<Event> {
    @NotNull
    private final String registerName;
    @NotNull
    private final Map<Class<Event>, Function<Event, PSkillResult>> allSkills = new HashMap<>();
    @NotNull
    private final LinkedList<Class<Event>> allEventTypes = new LinkedList<>();
    @NotNull
    private final AggregatedEventTypeArgs eventTypeArgs = new AggregatedEventTypeArgs();
    private boolean isShownInTooltip = true;
    private boolean built = false;
    private int triggerPriority = 0;
    private boolean isBannedWhenBroken = true;
    private int coolDownTicks = 0;
    private boolean hasCoolDown = false;

    public AggregatePassiveSkill(@NotNull String registerName) {
        this.registerName = registerName;
    }

    @NotNull
    @Override
    public IEventTypeArgs getEventTypeArgs() {
        return eventTypeArgs;
    }

    @NotNull
    @Override
    public PSkillResult onTrigger(@NotNull Event event) {
        Function<Event, PSkillResult> trigger = allSkills.get(event.getClass());
        if (trigger == null) {
            return PSkillResult.Fail;
        } else {
            return trigger.apply(event);
        }
    }

    public <T extends Event> AggregatePassiveSkill add(Class<T> clz, Function<T, PSkillResult> skill) {
        allSkills.put((Class<Event>) clz, (Function<Event, PSkillResult>) skill);
        allEventTypes.add((Class<Event>) clz);
        return this;
    }

    @Override
    public int getTriggerPriority() {
        return triggerPriority;
    }

    public AggregatePassiveSkill setTriggerPriority(int triggerPriority) {
        this.triggerPriority = triggerPriority;
        return this;
    }

    @Override
    public boolean isBanedWhenBroken() {
        return isBannedWhenBroken;
    }

    /**
     * The default is true.
     */
    public AggregatePassiveSkill setBanedWhenBroken(boolean banedWhenBroken) {
        this.isBannedWhenBroken = banedWhenBroken;
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

    @NotNull
    public AggregatePassiveSkill setCoolDownTicks(int coolDownTicks) {
        this.coolDownTicks = coolDownTicks;
        return this;
    }

    /**
     * Register itself to {@link SkillRegistry} and you should call it only once.
     */
    public AggregatePassiveSkill build() {
        SkillRegistry.register(this);
        built = true;
        return this;
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

    public boolean aggregate(AggregatePassiveSkill other) {
        if (!built) {
            for (Map.Entry<Class<Event>, Function<Event, PSkillResult>> entry : other.allSkills.entrySet()) {
                this.add(entry.getKey(), entry.getValue());
            }
            return true;
        }
        return false;
    }

    public boolean aggregate(PassiveSkill<Event> other) {
        if (!built) {
            this.add(other.getEventTypeArgs().getAllEventTypes().get(0), other::onTrigger);
            return true;
        }
        return false;
    }

    public AggregatePassiveSkill setShownInTooltip() {
        this.isShownInTooltip = true;
        return this;
    }

    public class AggregatedEventTypeArgs implements IEventTypeArgs {

        @Override
        public boolean has(Class<Event> eventType) {
            return allEventTypes.contains(eventType);
        }

        @Override
        public List<Class<Event>> getAllEventTypes() {
            return allEventTypes;
        }
    }
}
