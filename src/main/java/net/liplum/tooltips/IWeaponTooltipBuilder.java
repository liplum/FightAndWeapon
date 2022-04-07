package net.liplum.tooltips;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface IWeaponTooltipBuilder {
    @NotNull
    ITooltip build();
}
