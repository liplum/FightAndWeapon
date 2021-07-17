package net.liplum.tooltips;

import net.liplum.lib.Delegate;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TooltipPipe {
    public static final TooltipPipe Empty = new TooltipPipe();

    @Nonnull
    private final LinkedList<IMiddleware> allMiddlewares = new LinkedList<>();

    public TooltipPipe addMiddleware(@Nonnull IMiddleware middleware) {
        allMiddlewares.add(middleware);
        return this;
    }

    @Nonnull
    public ITooltip stream(@Nonnull TooltipContext context) {
        return new ITooltip() {
            @Nonnull
            @Override
            public List<String> getTooltip() {
                /*int middlewareCount = allMiddlewares.size();
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
                }*/

                //New
                Delegate<MiddlewareThroughInArgs> onThroughIn = new Delegate<>();
                Delegate<MiddlewareThroughOutArgs> onThroughOut = new Delegate<>();
                LinkedList<TooltipPart> finalTooltipParts = new LinkedList<>();
                List<IMiddleware> allThroughable = allMiddlewares.stream().filter(IThroughable.class::isInstance).collect(Collectors.toList());
                int middlewareCount = allThroughable.size();
                int currentIndex = 0;
                for (IMiddleware middleware : allMiddlewares) {
                    if (middleware instanceof IThroughable) {

                        IThroughable throughable = (IThroughable) middleware;
                        IPipeContext pipeContext = new PipeContext(context, middlewareCount)
                                .setCurrentIndex(currentIndex);
                        TooltipPart throughInTooltip = new TooltipPart();
                        MiddlewareThroughInArgs inArgs =
                                new MiddlewareThroughInArgs(middleware, throughInTooltip, pipeContext);
                        onThroughIn.invoke(inArgs);

                        TooltipPart middlewareResult = throughable.through(pipeContext);

                        TooltipPart throughOutTooltip = new TooltipPart();
                        MiddlewareThroughOutArgs outArgs =
                                new MiddlewareThroughOutArgs(middleware, middlewareResult, throughOutTooltip, pipeContext);
                        onThroughOut.invoke(outArgs);
                        finalTooltipParts.add(throughInTooltip.add(middlewareResult).add(throughOutTooltip));
                        currentIndex++;

                    } else if (middleware instanceof IMiddlewareQualifier) {

                        IMiddlewareQualifier qualifier = (IMiddlewareQualifier) middleware;
                        //Middleware subscribed the in and out event.
                        qualifier.onSubscribe(onThroughIn, onThroughOut);
                    }
                }

                //Generate final tooltip
                LinkedList<String> finalTooltip = new LinkedList<>();
                for (TooltipPart part : finalTooltipParts) {
                    List<String> tooltip = part.getTooltips();
                    finalTooltip.addAll(tooltip);
                }
                return finalTooltip;
            }
        };
    }

    private static class PipeContext implements IPipeContext {
        @Nonnull
        private final TooltipContext context;
        private final int allMiddlewareCount;

        private int currentIndex = 0;

        public PipeContext(@Nonnull TooltipContext context, int allMiddlewareCount) {
            this.context = context;
            this.allMiddlewareCount = allMiddlewareCount;
        }

        public int getCurrentIndex() {
            return currentIndex;
        }

        public PipeContext setCurrentIndex(int currentIndex) {
            this.currentIndex = currentIndex;
            return this;
        }

        @Nonnull
        public TooltipContext getContext() {
            return context;
        }

        public int getAllMiddlewareCount() {
            return allMiddlewareCount;
        }
    }
}
