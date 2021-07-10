package net.liplum.api.fight;

import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public class EventTypeArgs implements IEventTypeArgs {
    @Nonnull
    private final Class<? extends Event> clz;
    @Nonnull
    private final LinkedList<Class<? extends Event>> eventType;

    public EventTypeArgs(@Nonnull Class<? extends Event> clz) {
        this.clz = clz;
        this.eventType = new LinkedList<>();
        this.eventType.add(clz);
    }

    @Override
    public boolean has(Class<? extends Event> eventType) {
        return clz == eventType;
    }

    @Override
    public List<Class<? extends Event>> getAllEventType() {
        return eventType;
    }

}
