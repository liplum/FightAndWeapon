package net.liplum.lib.gui;

import net.liplum.lib.Event;
import org.jetbrains.annotations.NotNull;

public interface INotifyPropertyChanged<T> {
    @NotNull
    Event<IPropertySubscriber<T>> getPropertyChangedEvent();
}
