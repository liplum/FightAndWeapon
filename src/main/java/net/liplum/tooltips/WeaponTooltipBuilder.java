package net.liplum.tooltips;

import javax.annotation.Nonnull;

public class WeaponTooltipBuilder implements IWeaponTooltipBuilder {
    @Nonnull
    private final TooltipContext context;

    @Nonnull
    private final TooltipPipe pipe;

    public WeaponTooltipBuilder(@Nonnull TooltipContext context) {
        this.context = context;
        this.pipe = context.weaponCore.getTooltipPipe();
    }

    @Nonnull
    @Override
    public ITooltip build() {
        return pipe.stream(context);
    }
}
