package net.liplum.tooltips;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public class TooltipPipe {
    public static final TooltipPipe Empty = new TooltipPipe();
    @Nonnull
    private final LinkedList<ITooltipMiddleware> allMiddlewares = new LinkedList<>();

    public TooltipPipe addMiddleware(@Nonnull ITooltipMiddleware middleware) {
        allMiddlewares.add(middleware);
        return this;
    }

    public boolean removeMiddleware(ITooltipMiddleware middleware) {
        return allMiddlewares.remove(middleware);
    }

    @Nonnull
    public ITooltip stream(@Nonnull TooltipContext context) {
        return new ITooltip() {
            @Nonnull
            @Override
            public List<String> getTooltip() {
                TooltipPart allParts = TooltipPart.head();
                for (ITooltipMiddleware middleware : allMiddlewares) {
                    allParts.add(middleware.through(context));
                }
                return allParts.merge();
            }
        };
    }
}
