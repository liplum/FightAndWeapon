package net.liplum.tooltips;

import org.jetbrains.annotations.NotNull;

public class MiddlewareThroughInArgs {
    @NotNull
    public final IMiddleware middleware;

    @NotNull
    public final TooltipPart thisResult;

    @NotNull
    public final IPipeContext pipeContext;

    public MiddlewareThroughInArgs(@NotNull IMiddleware middleware, @NotNull TooltipPart thisResult, @NotNull IPipeContext pipeContext) {
        this.middleware = middleware;
        this.thisResult = thisResult;
        this.pipeContext = pipeContext;
    }
}
