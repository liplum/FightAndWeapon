package net.liplum.tooltips;

import javax.annotation.Nonnull;

public class MiddlewareThroughOutArgs {
    @Nonnull
    public final ITooltipMiddleware middleware;

    @Nonnull
    public final TooltipPart middlewareResult;

    @Nonnull
    public final TooltipPart thisResult;

    public MiddlewareThroughOutArgs(@Nonnull ITooltipMiddleware middleware, @Nonnull TooltipPart middlewareResult, @Nonnull TooltipPart thisResult) {
        this.middleware = middleware;
        this.middlewareResult = middlewareResult;
        this.thisResult = thisResult;
    }
}
