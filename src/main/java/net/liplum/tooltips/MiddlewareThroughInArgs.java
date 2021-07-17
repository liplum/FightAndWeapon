package net.liplum.tooltips;

import javax.annotation.Nonnull;

public class MiddlewareThroughInArgs {
    @Nonnull
    public final IMiddleware middleware;

    @Nonnull
    public final TooltipPart thisResult;

    @Nonnull
    public final IPipeContext pipeContext;

    public MiddlewareThroughInArgs(@Nonnull IMiddleware middleware, @Nonnull TooltipPart thisResult, @Nonnull IPipeContext pipeContext) {
        this.middleware = middleware;
        this.thisResult = thisResult;
        this.pipeContext = pipeContext;
    }
}
