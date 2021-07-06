package net.liplum.attributes;

import javax.annotation.Nonnull;

public interface IAttrModifierBuilder {
    IAttrModifierBuilder set(@Nonnull Attribute attribute, @Nonnull AttrModifier modifier);
}
