package net.liplum.lib.gui;

import net.liplum.lib.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class Property<T> implements INotifyPropertyChanged<T>, Supplier<T> {
    private final Event<IPropertySubscriber<T>> event = new PropertyChangedEvent();
    private T value;

    public Property(T value) {
        this.value = value;
    }

    public Property() {

    }

    @Nonnull
    public Event<IPropertySubscriber<T>> getPropertyChangedEvent() {
        return event;
    }

    public boolean set(@Nullable T newValue) {
        if (value != newValue) {
            value = newValue;
            onChanged();
            return true;
        }
        return false;
    }

    @Nullable
    public T get() {
        return value;
    }

    private void onChanged() {
        event.trigger();
    }

    public class PropertyChangedEvent extends Event<IPropertySubscriber<T>> {

        @Override
        public void trigger() {
            for (IPropertySubscriber<T> subscriber : subscribers) {
                subscriber.onChanged(new PropertyChangedArgs<>(value));
            }
        }
    }
}
