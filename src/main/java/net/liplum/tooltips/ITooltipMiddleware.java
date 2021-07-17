package net.liplum.tooltips;

import net.liplum.lib.Delegate;

import javax.annotation.Nonnull;

public interface ITooltipMiddleware {
    @Nonnull
    TooltipPart through(@Nonnull TooltipContext context);

    default void onSubscribe(Delegate<MiddlewareThroughInArgs> onMiddlewareThroughIn, Delegate<MiddlewareThroughInArgs> onMiddlewareThroughOut) {
    }
}
