package net.liplum.attributes;

import net.liplum.api.annotations.LongSupport;

@LongSupport
public class BasicAttrValue extends AttrValue {
    private final Number value;

    public BasicAttrValue(DataType dataType, Number value) {
        super(dataType);
        this.value = value;
    }

    public Number getNumber() {
        return value;
    }

    public int getInt() {
        return value.intValue();
    }

    public float getFloat() {
        return value.floatValue();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BasicAttrValue) {
            BasicAttrValue b = (BasicAttrValue) obj;
            return super.equals(obj) &&
                    b.value.equals(this.value);
        }
        return false;
    }
}
