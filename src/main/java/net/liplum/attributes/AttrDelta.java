package net.liplum.attributes;

import net.liplum.api.annotations.LongSupport;

@LongSupport
public class AttrDelta extends AttrValue {

    protected final Number delta;

    public AttrDelta(DataType dataType, Number delta) {
        super(dataType);
        this.delta = delta;
    }

    public AttrDelta(DataType dataType) {
        super(dataType);
        this.delta = 0;
    }

    public Number getDelta() {
        return delta;
    }
}
