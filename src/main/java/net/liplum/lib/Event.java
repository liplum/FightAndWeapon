package net.liplum.lib;

import java.util.LinkedList;
import java.util.List;

public abstract class Event<S> {
    protected final List<S> subscribers = new LinkedList<>();

    public void addSubscriber(S subscriber) {
        subscribers.add(subscriber);
    }

    public boolean removeSubscriber(S subscriber) {
        return subscribers.remove(subscriber);
    }

    public abstract void trigger();
}
