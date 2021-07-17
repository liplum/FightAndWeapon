package net.liplum.tooltips;

import javax.annotation.Nonnull;

public interface IPipeContext {
    @Nonnull
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
