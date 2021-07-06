package net.liplum.attributes;

import javax.annotation.Nonnull;

public interface IBasicAttrValueBuilder {
    IBasicAttrValueBuilder set(@Nonnull Attribute attribute, @Nonnull BasicAttrValue value);
}
