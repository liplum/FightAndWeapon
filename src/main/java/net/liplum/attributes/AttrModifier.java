package net.liplum.attributes;

import net.liplum.api.annotations.LongSupport;

@LongSupport
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AttrModifier) {
            AttrModifier b = (AttrModifier) obj;
            return super.equals(obj) &&
                    b.deltaRate.equals(this.deltaRate);
        }
        return false;
    }
}
