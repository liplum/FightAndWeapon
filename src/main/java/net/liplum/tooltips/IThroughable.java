package net.liplum.tooltips;

import javax.annotation.Nonnull;

public interface IThroughable extends IMiddleware {
    @Nonnull
    TooltipPart through(@Nonnull IPipeContext context);
}
