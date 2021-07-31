package net.liplum.attributes;

import net.liplum.api.annotations.Developing;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.fight.FawArgsGetter;

import javax.annotation.Nonnull;

@LongSupport
public interface IAttrCalculator extends FawArgsGetter {
    @Developing
    boolean isUseSpecialValueWhenWeaponBroken();

    @LongSupport
    boolean isPostEvent();

    @Nonnull
    @LongSupport
    FinalAttrValue calcu(@Nonnull IAttribute attribute);
}
