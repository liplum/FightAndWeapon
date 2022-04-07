package net.liplum.tooltips;

import org.jetbrains.annotations.NotNull;

public class WeaponTooltipBuilder implements IWeaponTooltipBuilder {
    @NotNull
    private final TooltipContext context;

    @NotNull
    private final TooltipPipe pipe;

    public WeaponTooltipBuilder(@NotNull TooltipContext context) {
        this.context = context;
        this.pipe = context.weaponCore.getTooltipPipe();
    }

    @NotNull
    @Override
    public ITooltip build() {
        return pipe.stream(context);
    }
}
