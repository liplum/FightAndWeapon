package net.liplum.items.weapons.lance;

import net.liplum.api.weapon.IModifier;

public abstract class LanceModifier implements IModifier<LanceCore> {
    public float getSprintLengthDelta() {
        return 0;
    }

    public float getSprintLengthRate() {
        return 0;
    }

    public boolean releaseSkill(LanceCore core, LanceArgs args) {
        return core.releaseSkill(args);
    }
}
