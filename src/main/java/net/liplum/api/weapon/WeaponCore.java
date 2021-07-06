package net.liplum.api.weapon;

import net.liplum.attributes.Attribute;
import net.liplum.attributes.BasicAttrValue;
import net.liplum.attributes.IAttributeProvider;
import net.liplum.attributes.IBasicAttrValueBuilder;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WeaponCore implements IAttributeProvider<BasicAttrValue> {
    private final Map<Attribute, BasicAttrValue> allAttributes = new HashMap<>();

    public WeaponCore() {
        AttributeBuilder builder = Attribute.genAllBasicAttrValue(new AttributeBuilder());
        for (Attribute attribute : initAllAttributes()) {
            builder.set(attribute, attribute.genBasicAttrValue());
        }
        buildAttributes(builder);
    }

    protected abstract List<Attribute> initAllAttributes();

    /**
     * Get the corresponding value via the attribute
     *
     * @param attribute the attribute
     * @return the value or delta which can be used to compute final attribute value.<br/>
     * If this didn't contain any attribute value which can match the attribute type, it would return null.
     */
    @Override
    public BasicAttrValue getValue(@Nonnull Attribute attribute) {
        return allAttributes.get(attribute);
    }

    protected abstract void buildAttributes(AttributeBuilder builder);

    @Nonnull
    public abstract WeaponType getWeaponType();

    protected class AttributeBuilder implements IBasicAttrValueBuilder {
        @Override
        public AttributeBuilder set(@Nonnull Attribute attribute, @Nonnull BasicAttrValue value) {
            allAttributes.put(attribute, value);
            return this;
        }
    }
}
