package net.liplum.tooltips;

import javax.annotation.Nonnull;

public interface ITooltipMiddleware {
    @Nonnull
    TooltipPart through(@Nonnull TooltipContext context);
}
