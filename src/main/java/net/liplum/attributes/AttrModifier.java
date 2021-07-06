package net.liplum.attributes;

public class AttrModifier extends AttrDelta {
    private final float deltaRate;

    public AttrModifier(DataType dataType, Number delta, float deltaRate) {
        super(dataType, delta);
        this.deltaRate = deltaRate;
    }

    public AttrModifier(DataType dataType) {
        super(dataType, 0);
        this.deltaRate = 0;
    }

    public float getDeltaRate() {
        return deltaRate;
    }
}
