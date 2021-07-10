package net.liplum.api.fight;

import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;

public interface IEventTypeArgs{
    boolean has(Class<? extends Event> eventType);

    List<Class<? extends Event>> getAllEventType();
}
