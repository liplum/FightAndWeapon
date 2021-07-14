package net.liplum.attributes;

import javax.annotation.Nonnull;

public interface IBasicAttrValueBuilder {
    IBasicAttrValueBuilder set(@Nonnull IAttribute attribute, @Nonnull BasicAttrValue value);
}
