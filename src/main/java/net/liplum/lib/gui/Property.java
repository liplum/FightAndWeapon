package net.liplum.lib.gui;

import net.liplum.lib.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Property<R> {
    private R value;
    private final Event<IPropertySubscriber<R>> event = new PropertyChangedEvent(this);

    public Property(R value) {
        this.value = value;
    }

    public Property() {

    }

    @Nonnull
    public Event<IPropertySubscriber<R>> getEvent() {
        return event;
    }

    public boolean set(@Nullable R newValue) {
        if (value != newValue) {
            value = newValue;
            onChanged();
            return true;
        }
        return false;
    }

    @Nullable
    public R get() {
        return value;
    }

    private void onChanged() {
        event.trigger();
    }

    public class PropertyChangedEvent extends Event<IPropertySubscriber<R>> {

        protected Property<R> property;

        public PropertyChangedEvent(Property<R> property) {
            this.property = property;
        }

        @Override
        public void trigger() {
            for (IPropertySubscriber<R> subscriber : subscribers) {
                subscriber.onChanged(property);
            }
        }
    }
}
