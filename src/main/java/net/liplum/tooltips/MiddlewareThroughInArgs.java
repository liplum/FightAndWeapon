package net.liplum.tooltips;

import javax.annotation.Nonnull;

public class MiddlewareThroughInArgs {
    @Nonnull
    public final ITooltipMiddleware middleware;

    @Nonnull
    public final TooltipPart thisResult;

    public MiddlewareThroughInArgs(@Nonnull ITooltipMiddleware middleware, @Nonnull TooltipPart thisResult) {
        this.middleware = middleware;
        this.thisResult = thisResult;
    }
}
