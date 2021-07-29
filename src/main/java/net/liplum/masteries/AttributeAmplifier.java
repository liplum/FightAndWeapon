package net.liplum.masteries;

import net.liplum.attributes.DataType;

import javax.annotation.Nonnull;

public class AttributeAmplifier {
    /**
     * You should use this constructor directly.
     */
    public AttributeAmplifier() {
    }

    @Nonnull
    private String attributeName = "";
    @Nonnull
    private DataType type = DataType.Int;
    @Nonnull
    private Number value = 0;

    @Nonnull
    public String getAttributeName() {
        return attributeName;
    }

    public AttributeAmplifier setAttributeName(@Nonnull String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    @Nonnull
    public DataType getType() {
        return type;
    }

    public AttributeAmplifier setType(@Nonnull DataType type) {
        this.type = type;
        return this;
    }

    @Nonnull
    public Number getValue() {
        return value;
    }

    public AttributeAmplifier setValue(@Nonnull Number value) {
        this.value = value;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AttributeAmplifier){
            AttributeAmplifier amp = (AttributeAmplifier) obj;
            return this.attributeName.equals(amp.attributeName) &&
                    this.type.equals(amp.getType())&&
                    this.value.equals(amp.value);
        }
        return false;
    }
}
