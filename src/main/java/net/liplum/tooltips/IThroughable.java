package net.liplum.tooltips;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface IThroughable extends IMiddleware {
    @NotNull
    TooltipPart through(@NotNull IPipeContext context);
}
