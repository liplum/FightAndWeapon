package net.liplum.lib.gui;

import net.liplum.lib.Event;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ObservableCollection<T> implements INotifyPropertyChanged<List<T>> {
    private final Event<IPropertySubscriber<List<T>>> event = new ObservableCollectionEvent();

    @NotNull
    private final ArrayList<T> arrayList = new ArrayList<>();

    private ArrayList<T> getList() {
        return arrayList;
    }

    @NotNull
    @Override
    public Event<IPropertySubscriber<List<T>>> getPropertyChangedEvent() {
        return event;
    }

    public void raise() {
        event.trigger();
    }

    public T get(int index) {
        return getList().get(index);
    }

    public boolean add(T t) {
        boolean added = getList().add(t);
        raise();
        return added;
    }

    public void add(int index, T element) {
        getList().add(index, element);
        raise();
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        boolean added = getList().addAll(index, c);
        raise();
        return added;
    }

    public boolean addAll(Collection<? extends T> c) {
        boolean added = getList().addAll(c);
        raise();
        return added;
    }

    public T remove(int index) {
        T removed = getList().remove(index);
        raise();
        return removed;
    }

    public boolean remove(Object o) {
        boolean removed = getList().remove(o);
        raise();
        return removed;
    }

    public int size() {
        return getList().size();
    }

    private class ObservableCollectionEvent extends Event<IPropertySubscriber<List<T>>> {

        @Override
        public void trigger() {
            for (IPropertySubscriber<List<T>> subscriber : subscribers) {
                subscriber.onChanged(new PropertyChangedArgs<>(getList()));
            }
        }
    }
}
