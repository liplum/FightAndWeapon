package net.liplum.attributes;

public class AttrDelta extends AttrValue {

    public AttrDelta(DataType dataType, Number delta) {
        super(dataType);
        this.delta = delta;
    }

    protected final Number delta;

    public Number getDelta(){
        return delta;
    }
}
