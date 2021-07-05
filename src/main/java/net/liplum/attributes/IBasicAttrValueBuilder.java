package net.liplum.attributes;

import javax.annotation.Nonnull;

public interface IBasicAttrValueBuilder {
    IBasicAttrValueBuilder add(@Nonnull Attribute attribute, @Nonnull BasicAttrValue value);
}
