package net.liplum.tooltips;

import org.jetbrains.annotations.NotNull;

public interface IPipeContext {
    @NotNull
    TooltipContext getContext();

    int getAllMiddlewareCount();

    int getCurrentIndex();

    default boolean isFirstMiddleware() {
        return getCurrentIndex() == 0;
    }

    default boolean isLastMiddleware() {
        return getCurrentIndex() == getAllMiddlewareCount() - 1;
    }

    default boolean hasAnyFollowingMiddleware() {
        return !isLastMiddleware();
    }
}
