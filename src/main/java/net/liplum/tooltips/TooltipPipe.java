package net.liplum.tooltips;

import net.liplum.lib.Delegate;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class TooltipPipe {
    public static final TooltipPipe Empty = new TooltipPipe();

    @Nonnull
    private final LinkedList<ITooltipMiddleware> allMiddlewares = new LinkedList<>();

    public TooltipPipe addMiddleware(@Nonnull ITooltipMiddleware middleware) {
        allMiddlewares.add(middleware);
        return this;
    }

    @Nonnull
    public ITooltip stream(@Nonnull TooltipContext context) {
        return new ITooltip() {
            @Nonnull
            @Override
            public List<String> getTooltip() {
                int middlewareCount = allMiddlewares.size();
                ArrayList<TooltipEntry> allEntries = new ArrayList<>(middlewareCount);
                ArrayList<ITooltipMiddleware> middlewareSequence = new ArrayList<>(middlewareCount);
                ListIterator<ITooltipMiddleware> it = allMiddlewares.listIterator();
                while (it.hasNext()) {
                    ITooltipMiddleware current = it.next();
                    context.setHasNextMiddleware(it.hasNext());
                    TooltipPart result = current.through(context);
                    context.setLastPart(result);
                    allEntries.add(new TooltipEntry(current, result));
                    middlewareSequence.add(current);
                }

                context.setLastPart(null);
                for (int i = 0; i < middlewareCount; i++) {
                    ITooltipMiddleware current = middlewareSequence.get(i);
                    for (int j = 0; j < middlewareCount; j++) {
                        List<TooltipEntry> restEntries = allEntries.subList(j, middlewareCount);
                        TooltipEntry entry = allEntries.get(j);
                        context.setHasNextMiddleware(j != middlewareCount - 1);
                        ITooltipMiddleware middleware = entry.middleware;
                        TooltipPart result = entry.result;
                        TooltipPart preTooltip = new TooltipPart();
                        current.onFollowingMiddlewareThroughIn(
                                new TooltipEntry(middleware, preTooltip),
                                restEntries,
                                context);
                        List<String> preTooltips = preTooltip.getTooltips();
                        if (preTooltips != null) {
                            for (String str : preTooltips) {
                                result.addHead(str);
                            }
                        }
                        current.onFollowingMiddlewareThroughOut(entry,
                                restEntries,
                                context);
                        context.setLastPart(result);
                    }
                }

                //New
                Delegate<MiddlewareThroughInArgs> onThroughIn = new Delegate<>();
                Delegate<MiddlewareThroughInArgs> onThroughOut = new Delegate<>();
                for (ITooltipMiddleware middleware : allMiddlewares) {
                    MiddlewareThroughInArgs inArgs = new MiddlewareThroughInArgs();
                    onThroughIn.invoke(inArgs);



                    MiddlewareThroughInArgs outArgs = new MiddlewareThroughInArgs();
                    onThroughOut.invoke(outArgs);

                    //Middleware subscribed the in and out event.
                    middleware.onSubscribe(onThroughIn, onThroughOut);
                }

                //Generate final tooltip
                LinkedList<String> finalTooltip = new LinkedList<>();
                for (TooltipEntry entry : allEntries) {
                    List<String> tooltip = entry.result.getTooltips();
                    if (tooltip != null) {
                        finalTooltip.addAll(tooltip);
                    }
                }
                return finalTooltip;
            }
        };
    }
}
