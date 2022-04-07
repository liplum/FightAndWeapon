package net.liplum.attributes;

import net.liplum.api.annotations.LongSupport;
import org.jetbrains.annotations.NotNull;

public interface IAttrModifierBuilder {
    @NotNull
    @LongSupport
    IAttrModifierBuilder set(@NotNull IAttribute attribute, @NotNull AttrModifier modifier);

    @NotNull
    @LongSupport
    default IAttrModifierBuilder set(@NotNull IAttribute attribute, @NotNull Number delta, float rate) {
        return this.set(attribute, attribute.newAttrModifier(delta, rate));
    }
}
