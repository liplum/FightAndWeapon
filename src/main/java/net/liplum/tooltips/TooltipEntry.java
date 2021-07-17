package net.liplum.tooltips;

public class TooltipEntry {
    public final ITooltipMiddleware middleware;
    public final TooltipPart result;

    public TooltipEntry(ITooltipMiddleware middleware, TooltipPart result) {
        this.middleware = middleware;
        this.result = result;
    }
}
