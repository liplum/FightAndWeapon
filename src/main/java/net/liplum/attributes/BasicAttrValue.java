package net.liplum.attributes;

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
}
