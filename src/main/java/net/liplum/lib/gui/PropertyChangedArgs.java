package net.liplum.lib.gui;

public class PropertyChangedArgs<T> {
    private final T value;

    public PropertyChangedArgs(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
