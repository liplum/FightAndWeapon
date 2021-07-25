package net.liplum.lib;

import net.liplum.api.annotations.Developing;

import java.util.LinkedList;
import java.util.List;

@Developing
public abstract class Event<S> {
    protected final List<S> subscribers = new LinkedList<>();

    public void addSubscriber(S subscriber) {
        subscribers.add(subscriber);
    }

    public boolean removeSubscriber(S subscriber) {
        return subscribers.remove(subscriber);
    }

    public void clear() {
        subscribers.clear();
    }

    public abstract void trigger();
}
