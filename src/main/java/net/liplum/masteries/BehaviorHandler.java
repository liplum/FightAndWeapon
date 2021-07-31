package net.liplum.masteries;

import javax.annotation.Nonnull;

public abstract class BehaviorHandler implements IBehaviorHandler {
    @Nonnull
    private final Behavior behavior;

    public BehaviorHandler(@Nonnull Behavior behavior) {
        this.behavior = behavior;
        MasterySheet.register(this);
    }

    @Nonnull
    @Override
    public Behavior getBehavior() {
        return behavior;
    }
}