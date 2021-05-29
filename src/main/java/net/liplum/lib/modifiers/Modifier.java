package net.liplum.lib.modifiers;

import net.liplum.lib.weaponcores.IWeaponCore;

public abstract class Modifier<CoreType extends IWeaponCore> {
    public int getCoolDownDelta() {
        return 0;
    }
    public float getCoolDownRate() {
        return 0;
    }

    public float getStrengthDelta() {
        return 0;
    }

    public float getStrengthRate() {
        return 0;
    }

    public abstract CoreType getCoreType();
}
