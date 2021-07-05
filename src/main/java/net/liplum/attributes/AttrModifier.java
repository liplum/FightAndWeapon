package net.liplum.attributes;

public class AttrModifier extends AttrDelta {
    private final float rate;

    public AttrModifier(DataType dataType, Number delta, float rate) {
        super(dataType, delta);
        this.rate = rate;
    }

    public float getRate() {
        return rate;
    }
}
