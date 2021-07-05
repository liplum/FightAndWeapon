package net.liplum.attributes;

import javax.annotation.Nonnull;

public interface IAttrModifierBuilder {
    IAttrModifierBuilder add(@Nonnull Attribute attribute, @Nonnull AttrModifier modifier);
}
