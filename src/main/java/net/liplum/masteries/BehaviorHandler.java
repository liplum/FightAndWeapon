package net.liplum.masteries;

import net.liplum.api.registeies.WeaponActionHook;
import org.jetbrains.annotations.NotNull;

public abstract class BehaviorHandler implements IBehaviorHandler {
    @NotNull
    private final Behavior behavior;

    public BehaviorHandler(@NotNull Behavior behavior) {
        this.behavior = behavior;
        WeaponActionHook.register(this);
    }

    @NotNull
    @Override
    public Behavior getBehavior() {
        return behavior;
    }
}
