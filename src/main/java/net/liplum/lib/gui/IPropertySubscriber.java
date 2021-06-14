package net.liplum.lib.gui;

public interface IPropertySubscriber<T> {
    void onChanged(PropertyChangedArgs<T> args);
}
