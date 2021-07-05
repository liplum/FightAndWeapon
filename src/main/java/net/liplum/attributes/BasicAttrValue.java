package net.liplum.attributes;

public class BasicAttrValue extends AttrValue {
    private final Number value;

    public BasicAttrValue( Number value,DataType dataType) {
        super(dataType);
        this.value = value;
    }

    public int getInt(){
        return value.intValue();
    }

    public float getFloat(){
        return value.floatValue();
    }
}
