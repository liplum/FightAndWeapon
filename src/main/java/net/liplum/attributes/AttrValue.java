package net.liplum.attributes;

public abstract class AttrValue {
    protected final DataType dataType;

    public AttrValue(DataType dataType) {
        this.dataType = dataType;
    }

    public DataType getDataType() {
        return dataType;
    }
}
