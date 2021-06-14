package net.liplum.lib.gui;

import net.liplum.lib.Event;

import javax.annotation.Nonnull;

public interface INotifyPropertyChanged<T> {
    @Nonnull
    Event<IPropertySubscriber<T>> getPropertyChangedEvent();
}
