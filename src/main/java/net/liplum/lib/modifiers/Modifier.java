package net.liplum.lib.modifiers;

public abstract class Modifier {

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
}
