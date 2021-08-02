package net.liplum.tooltips;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface IWeaponTooltipBuilder {
    @Nonnull
    ITooltip build();
}
