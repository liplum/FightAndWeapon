package net.liplum.api.fight;

import net.liplum.api.registeies.SkillRegistry;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class AggregatedPassiveSkill implements IPassiveSkill<Event> {
    private boolean isShownInTooltip = true;
    @Nonnull
    private final String registerName;
    @Nonnull
    private final Map<Class<Event>, Function<Event, PSkillResult>> allSkills = new HashMap<>();
    @Nonnull
    private final LinkedList<Class<Event>> allEventTypes = new LinkedList<>();
    @Nonnull
    private final AggregatedEventTypeArgs eventTypeArgs = new AggregatedEventTypeArgs();

    private boolean built = false;

    public AggregatedPassiveSkill(@Nonnull String registerName) {
        this.registerName = registerName;
    }

    @Nonnull
    @Override
    public IEventTypeArgs getEventTypeArgs() {
        return eventTypeArgs;
    }

    @Nonnull
    @Override
    public PSkillResult onTrigger(@Nonnull Event event) {
        return allSkills.get(event.getClass()).apply(event);
    }

    public AggregatedPassiveSkill add(Class<Event> clz, Function<Event, PSkillResult> skill) {
        allSkills.put(clz, skill);
        allEventTypes.add(clz);
        return this;
    }


    /**
     * Register itself to {@link SkillRegistry} and you should call it only once.
     */
    public AggregatedPassiveSkill build() {
        SkillRegistry.register(this);
        built = true;
        return this;
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

    public boolean aggregate(AggregatedPassiveSkill other) {
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

    public AggregatedPassiveSkill setShownInTooltip() {
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
