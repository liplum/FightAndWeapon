package net.liplum.items.weapons.harp;

import net.liplum.api.weapon.MagicToolModifier;

public abstract class HarpModifier implements MagicToolModifier<HarpCore> {
    public double getRadiusDelta() {
        return 0;
    }

    public float getRadiusRate() {
        return 0;
    }

    public int getMaxUseDelta() {
        return 0;
    }

    public float getMaxUseRate() {
        return 0;
    }

    public int getFrequencyDelta() {
        return 0;
    }

    public float getFrequencyRate() {
        return 0;
    }

    public boolean continueSkill(HarpCore core, ContinuousHarpArgs args) {
        return core.continueSkill(args);
    }

    public boolean releaseSkill(HarpCore core, SingleHarpArgs args) {
        return core.releaseSkill(args);
    }
}
