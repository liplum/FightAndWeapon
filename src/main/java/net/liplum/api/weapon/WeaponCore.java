package net.liplum.api.weapon;

import net.liplum.attributes.Attribute;
import net.liplum.attributes.BasicAttrValue;
import net.liplum.attributes.IAttributeProvider;
import net.liplum.attributes.IBasicAttrValueBuilder;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class WeaponCore implements IAttributeProvider<BasicAttrValue> {
    private final Map<Attribute, BasicAttrValue> AttributeValueMap = new HashMap<>();
    protected List<Attribute> allAttributes = new LinkedList<>();

    public WeaponCore() {
        AttributeBuilder builder = new AttributeBuilder();
        initAllAttributes(allAttributes);
        for (Attribute attribute : allAttributes) {
            builder.set(attribute, attribute.emptyBasicAttrValue());
        }
        buildAttributes(builder);
    }

    protected void initAllAttributes(List<Attribute> attributes) {
        attributes.addAll(Attribute.getAllBasicAttributes());
    }

    /**
     * Get the corresponding value via the attribute
     *
     * @param attribute the attribute
     * @return the value or delta which can be used to compute final attribute value.<br/>
     * If this didn't contain any attribute value which can match the attribute type, it would return null.
     */
    @Override
    public BasicAttrValue getValue(@Nonnull Attribute attribute) {
        return AttributeValueMap.get(attribute);
    }

    protected abstract void buildAttributes(AttributeBuilder builder);

    @Nonnull
    public abstract WeaponType getWeaponType();

    protected class AttributeBuilder implements IBasicAttrValueBuilder {
        @Override
        public AttributeBuilder set(@Nonnull Attribute attribute, @Nonnull BasicAttrValue value) {
            AttributeValueMap.put(attribute, value);
            return this;
        }
    }
}
