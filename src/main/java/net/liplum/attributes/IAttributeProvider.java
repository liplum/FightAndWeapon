package net.liplum.attributes;

import javax.annotation.Nonnull;

public interface IAttributeProvider<T extends AttrValue> {
    /**
     * Get the corresponding value via the attribute
     *
     * @param attribute the attribute
     * @return the value or delta which can be used to compute final attribute value.<br/>
     * If this didn't contain any attribute value which can match the attribute type, it would return null.
     */
    T getValue(@Nonnull Attribute attribute);
}
