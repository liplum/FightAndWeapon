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
        public void onChanged(Property<T> property) {
            target.set(map.apply(property.get()));
        }
    };

    private final IPropertySubscriber<T> onTargetChanged = new IPropertySubscriber<T>() {
        @Override
        public void onChanged(Property<T> property) {
            source.set(map.apply(property.get()));
        }
    };


    public Binding(@Nonnull Property<T> source, @Nonnull Property<T> target, @Nonnull BindingMode mode) {
        this.mode = mode;
        this.source = source;
        this.target = target;
        if (mode.isSourceToTarget()) {
            source.getEvent().addSubscriber(onSourceChanged);
        }
        if (mode.isTargetToSource()) {
            target.getEvent().addSubscriber(onTargetChanged);
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
        source.getEvent().removeSubscriber(onSourceChanged);
        target.getEvent().removeSubscriber(onTargetChanged);
    }
}
