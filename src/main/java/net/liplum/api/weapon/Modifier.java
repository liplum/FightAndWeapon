package net.liplum.api.weapon;

import net.liplum.attributes.AttrModifier;
import net.liplum.attributes.Attribute;
import net.liplum.attributes.IAttrModifierBuilder;
import net.liplum.attributes.IAttributeProvider;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Modifier<CoreType extends WeaponCore> implements IAttributeProvider<AttrModifier> {
    @Nonnull
    private final Map<Attribute, AttrModifier> AttributeModifierMap = new HashMap<>();
    @Nonnull
    protected List<Attribute> allAttributes = new LinkedList<>();

    public Modifier() {
        initAllAttributes(allAttributes);

        ModifierBuilder builder = new ModifierBuilder();
        for (Attribute attribute : allAttributes) {
            builder.set(attribute, attribute.emptyAttrModifier());
        }
        build(builder);
    }

    protected void initAllAttributes(List<Attribute> attributes){
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
    public AttrModifier getValue(@Nonnull Attribute attribute) {
        return AttributeModifierMap.get(attribute);
    }

    protected abstract void build(ModifierBuilder builder);

    public abstract CoreType getCoreType();

    protected class ModifierBuilder implements IAttrModifierBuilder {
        @Override
        public ModifierBuilder set(@Nonnull Attribute attribute, @Nonnull AttrModifier modifier) {
            AttributeModifierMap.put(attribute, modifier);
            return this;
        }
    }
}
