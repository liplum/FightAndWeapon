package net.liplum.attributes;

import javax.annotation.Nonnull;

public interface IAttrModifierBuilder {
    IAttrModifierBuilder set(@Nonnull IAttribute attribute, @Nonnull AttrModifier modifier);
}
