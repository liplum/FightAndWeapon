package net.liplum.attributes;

import net.liplum.api.annotations.LongSupport;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@LongSupport
public interface IAttributeProvider<T extends AttrValue> {
    /**
     * Get the corresponding value via the attribute
     *
     * @param attribute the attribute
     * @return the value or delta which can be used to compute final attribute value.<br/>
     * If this didn't contain any attribute value which can match the attribute type, it would return null.
     */
    @Nullable
    @LongSupport
    T getValue(@Nonnull IAttribute attribute);
}
