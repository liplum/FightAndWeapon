package net.liplum.lib.modifiers;

import net.liplum.api.weapon.IModifier;
import net.liplum.lib.cores.lance.ILanceCore;
import net.liplum.lib.cores.lance.LanceArgs;

public abstract class LanceModifier implements IModifier<ILanceCore> {
    public float getSprintLengthDelta() {
        return 0;
    }

    public float getSprintLengthRate() {
        return 0;
    }

    public boolean releaseSkill(ILanceCore core, LanceArgs args) {
        return core.releaseSkill(args);
    }
}
