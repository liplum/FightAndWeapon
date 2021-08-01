package net.liplum.attributes;

import net.liplum.api.annotations.LongSupport;

import javax.annotation.Nonnull;

public interface IAttrModifierBuilder {
    @Nonnull
    @LongSupport
    IAttrModifierBuilder set(@Nonnull IAttribute attribute, @Nonnull AttrModifier modifier);

    @Nonnull
    @LongSupport
    default IAttrModifierBuilder set(@Nonnull IAttribute attribute, @Nonnull Number delta, float rate) {
        return this.set(attribute, attribute.newAttrModifier(delta, rate));
    }
}
