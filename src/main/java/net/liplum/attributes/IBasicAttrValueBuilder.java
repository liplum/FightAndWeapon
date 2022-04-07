package net.liplum.attributes;

import net.liplum.api.annotations.LongSupport;
import org.jetbrains.annotations.NotNull;

public interface IBasicAttrValueBuilder {
    @NotNull
    @LongSupport
    IBasicAttrValueBuilder set(@NotNull IAttribute attribute, @NotNull BasicAttrValue value);

    @NotNull
    @LongSupport
    default IBasicAttrValueBuilder set(@NotNull IAttribute attribute, @NotNull Number value) {
        return this.set(attribute, attribute.newBasicAttrValue(value));
    }
}
