package net.liplum.api.fight;

import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public class EventTypeArgs implements IEventTypeArgs {
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
