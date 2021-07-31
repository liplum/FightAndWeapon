package net.liplum.attributes;

import net.liplum.api.annotations.LongSupport;

@LongSupport
public abstract class AttrValue {
    protected final DataType dataType;

    public AttrValue(DataType dataType) {
        this.dataType = dataType;
    }

    public DataType getDataType() {
        return dataType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AttrValue) {
            AttrValue b = (AttrValue) obj;
            return b.dataType.equals(this.dataType);
        }
        return false;
    }
}
