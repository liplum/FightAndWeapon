package net.liplum.api.weapon;

import net.liplum.attributes.AttrModifier;
import net.liplum.attributes.Attribute;
import net.liplum.attributes.IAttrModifierBuilder;
import net.liplum.attributes.IAttributeProvider;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public abstract class Modifier<CoreType extends WeaponCore> implements IAttributeProvider<AttrModifier> {
    private final Map<Attribute, AttrModifier> allAttributeModifiers = new HashMap<>();

    public Modifier() {
        buildAttributes(Attribute.genAllAttrModifiers(new AttributeBuilder()));
    }

    /**
     * Get the corresponding value via the attribute
     *
     * @param attribute the attribute
     * @return the value or delta which can be used to compute final attribute value.<br/>
     * If this didn't contain any attribute value which can match the attribute type, it would return null.
     */
    @Override
    public AttrModifier getValue(@Nonnull Attribute attribute) {
        return allAttributeModifiers.get(attribute);
    }

    protected abstract void buildAttributes(AttributeBuilder builder);

    public abstract CoreType getCoreType();

    protected class AttributeBuilder implements IAttrModifierBuilder {
        @Override
        public AttributeBuilder add(@Nonnull Attribute attribute, @Nonnull AttrModifier modifier) {
            allAttributeModifiers.put(attribute, modifier);
            return this;
        }
    }
}
