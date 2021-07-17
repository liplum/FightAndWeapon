package net.liplum.tooltips;

import net.liplum.lib.Delegate;

public interface IMiddlewareQualifier extends IMiddleware {
    default void onSubscribe(Delegate<MiddlewareThroughInArgs> onMiddlewareThroughIn, Delegate<MiddlewareThroughOutArgs> onMiddlewareThroughOut) {
    }
}
