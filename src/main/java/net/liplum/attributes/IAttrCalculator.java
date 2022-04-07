package net.liplum.attributes;

import net.liplum.api.annotations.Developing;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.fight.FawArgsGetter;
import org.jetbrains.annotations.NotNull;

@LongSupport
public interface IAttrCalculator extends FawArgsGetter {
    @Developing
    boolean isUseSpecialValueWhenWeaponBroken();

    @LongSupport
    boolean isPostEvent();

    @NotNull
    @LongSupport
    FinalAttrValue calcu(@NotNull IAttribute attribute);
}
