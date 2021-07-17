package net.liplum.tooltips;

import javax.annotation.Nonnull;

public class MiddlewareThroughOutArgs {
    @Nonnull
    public final IMiddleware middleware;

    @Nonnull
    public final TooltipPart middlewareResult;

    @Nonnull
    public final TooltipPart thisResult;

    @Nonnull
    public final IPipeContext pipeContext;

    public MiddlewareThroughOutArgs(@Nonnull IMiddleware middleware, @Nonnull TooltipPart middlewareResult, @Nonnull TooltipPart thisResult, @Nonnull IPipeContext pipeContext) {
        this.middleware = middleware;
        this.middlewareResult = middlewareResult;
        this.thisResult = thisResult;
        this.pipeContext = pipeContext;
    }
}
