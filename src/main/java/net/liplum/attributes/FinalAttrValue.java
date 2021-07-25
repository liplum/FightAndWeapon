package net.liplum.attributes;

import net.liplum.api.annotations.LongSupport;

@LongSupport
public class FinalAttrValue extends BasicAttrValue {

    public FinalAttrValue(DataType dataType, Number value) {
        super(dataType, value);
    }
}
