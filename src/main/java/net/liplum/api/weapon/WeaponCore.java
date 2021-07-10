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
    @Nonnull
    private final Map<Attribute, BasicAttrValue> AttributeValueMap = new HashMap<>();
    @Nonnull
    protected List<Attribute> allAttributes = new LinkedList<>();
    @Nonnull
    protected IWeaponSkillPredicate weaponSkillPredicate;

    public WeaponCore() {
        weaponSkillPredicate = getWeaponType().getWeaponSkillPredicate();
        initAllAttributes(allAttributes);

        WeaponCoreBuilder builder = new WeaponCoreBuilder();
        for (Attribute attribute : allAttributes) {
            builder.set(attribute, attribute.emptyBasicAttrValue());
        }
        build(builder);
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

    protected abstract void build(WeaponCoreBuilder builder);

    @Nonnull
    public IWeaponSkillPredicate getWeaponSkillPredicate() {
        return weaponSkillPredicate;
    }

    @Nonnull
    public abstract WeaponType getWeaponType();

    protected class WeaponCoreBuilder implements IBasicAttrValueBuilder {
        @Override
        public WeaponCoreBuilder set(@Nonnull Attribute attribute, @Nonnull BasicAttrValue value) {
            AttributeValueMap.put(attribute, value);
            return this;
        }

        public WeaponCoreBuilder setWeaponSkillPredicate(@Nonnull IWeaponSkillPredicate weaponSkillPredicate) {
            WeaponCore.this.weaponSkillPredicate = weaponSkillPredicate;
            return this;
        }
    }
}