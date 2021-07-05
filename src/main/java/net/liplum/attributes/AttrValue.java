package net.liplum.attributes;

public abstract class AttrValue {
    public AttrValue(DataType dataType) {
        this.dataType = dataType;
    }

    protected final DataType dataType;

    public DataType getDataType() {
        return dataType;
    }
}
