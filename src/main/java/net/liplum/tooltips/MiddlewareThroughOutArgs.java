package net.liplum.tooltips;

import org.jetbrains.annotations.NotNull;

public class MiddlewareThroughOutArgs {
    @NotNull
    public final IMiddleware middleware;

    @NotNull
    public final TooltipPart middlewareResult;

    @NotNull
    public final TooltipPart thisResult;

    @NotNull
    public final IPipeContext pipeContext;

    public MiddlewareThroughOutArgs(@NotNull IMiddleware middleware, @NotNull TooltipPart middlewareResult, @NotNull TooltipPart thisResult, @NotNull IPipeContext pipeContext) {
        this.middleware = middleware;
        this.middlewareResult = middlewareResult;
        this.thisResult = thisResult;
        this.pipeContext = pipeContext;
    }
}
