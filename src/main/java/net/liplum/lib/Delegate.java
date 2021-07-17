package net.liplum.lib;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Delegate<Args> {
    protected final List<Consumer<Args>> callBacks = new LinkedList<>();

    public void add(Consumer<Args> subscriber) {
        callBacks.add(subscriber);
    }

    public boolean remove(Consumer<Args> subscriber) {
        return callBacks.remove(subscriber);
    }

    public void clear() {
        callBacks.clear();
    }

    public void invoke(Args args) {
        for (Consumer<Args> callBack : callBacks) {
            callBack.accept(args);
        }
    }
}
