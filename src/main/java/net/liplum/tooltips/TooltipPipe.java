package net.liplum.tooltips;

import net.liplum.lib.Delegate;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TooltipPipe {
    public static final TooltipPipe Empty = new TooltipPipe();

    @NotNull
    private final LinkedList<IMiddleware> allMiddlewares = new LinkedList<>();

    public TooltipPipe addMiddleware(@NotNull IMiddleware middleware) {
        allMiddlewares.add(middleware);
        return this;
    }

    @NotNull
    public ITooltip stream(@NotNull TooltipContext context) {
        return () -> {
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
        };
    }

    private static class PipeContext implements IPipeContext {
        @NotNull
        private final TooltipContext context;
        private final int allMiddlewareCount;

        private int currentIndex = 0;

        public PipeContext(@NotNull TooltipContext context, int allMiddlewareCount) {
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

        @NotNull
        public TooltipContext getContext() {
            return context;
        }

        public int getAllMiddlewareCount() {
            return allMiddlewareCount;
        }
    }
}
