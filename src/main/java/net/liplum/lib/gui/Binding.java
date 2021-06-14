package net.liplum.lib.gui;

import javax.annotation.Nonnull;
import java.util.function.Function;

public class Binding<T> {
    @Nonnull
    private final Property<T> source;
    @Nonnull
    private final Property<T> target;
    @Nonnull
    private final BindingMode mode;
    @Nonnull
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


    public Binding(@Nonnull Property<T> source, @Nonnull Property<T> target, @Nonnull BindingMode mode) {
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

    public Binding(@Nonnull Property<T> source, @Nonnull Property<T> target, @Nonnull BindingMode mode, @Nonnull Function<T, T> map) {
        this(source, target, mode);
        this.map = map;
    }

    @Nonnull
    public Property<T> getSource() {
        return source;
    }

    @Nonnull
    public Property<T> getTarget() {
        return target;
    }

    @Nonnull
    public BindingMode getMode() {
        return mode;
    }

    @Nonnull
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
