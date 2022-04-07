package net.liplum.lib.gui;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class Binding<T> {
    @NotNull
    private final Property<T> source;
    @NotNull
    private final Property<T> target;
    @NotNull
    private final BindingMode mode;
    @NotNull
    private Function<T, T> map = v -> v;

    private final IPropertySubscriber<T> onSourceChanged = new IPropertySubscriber<T>() {
        @Override
        public void onChanged(PropertyChangedArgs<T> args) {
            target.set(map.apply(args.getValue()));
        }
    };

    private final IPropertySubscriber<T> onTargetChanged = new IPropertySubscriber<T>() {
        @Override
        public void onChanged(PropertyChangedArgs<T> args) {
            source.set(map.apply(args.getValue()));
        }
    };


    public Binding(@NotNull Property<T> source, @NotNull Property<T> target, @NotNull BindingMode mode) {
        this.mode = mode;
        this.source = source;
        this.target = target;
        if (mode.isSourceToTarget()) {
            source.getPropertyChangedEvent().addSubscriber(onSourceChanged);
        }
        if (mode.isTargetToSource()) {
            target.getPropertyChangedEvent().addSubscriber(onTargetChanged);
        }
    }

    public Binding(@NotNull Property<T> source, @NotNull Property<T> target, @NotNull BindingMode mode, @NotNull Function<T, T> map) {
        this(source, target, mode);
        this.map = map;
    }

    @NotNull
    public Property<T> getSource() {
        return source;
    }

    @NotNull
    public Property<T> getTarget() {
        return target;
    }

    @NotNull
    public BindingMode getMode() {
        return mode;
    }

    @NotNull
    public Function<T, T> getMap() {
        return map;
    }

    /**
     * Once you destroy this binding, it will no longer work
     */
    public void destroy() {
        source.getPropertyChangedEvent().removeSubscriber(onSourceChanged);
        target.getPropertyChangedEvent().removeSubscriber(onTargetChanged);
    }
}
