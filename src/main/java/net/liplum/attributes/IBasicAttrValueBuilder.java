package net.liplum.attributes;

import net.liplum.api.annotations.LongSupport;

import javax.annotation.Nonnull;

public interface IBasicAttrValueBuilder {
    @Nonnull
    @LongSupport
    IBasicAttrValueBuilder set(@Nonnull IAttribute attribute, @Nonnull BasicAttrValue value);

    @Nonnull
    @LongSupport
    default IBasicAttrValueBuilder set(@Nonnull IAttribute attribute, @Nonnull Number value) {
        return this.set(attribute, attribute.newBasicAttrValue(value));
    }
}
