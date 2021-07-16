package net.liplum.tooltips;

import javax.annotation.Nonnull;
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
                LinkedList<TooltipPart> allParts = new LinkedList<>();
                ListIterator<ITooltipMiddleware> it = allMiddlewares.listIterator();
                while (it.hasNext()) {
                    ITooltipMiddleware middleware = it.next();
                    context.setHasNextMiddleware(it.hasNext());
                    TooltipPart part = middleware.through(context);
                    allParts.add(part);
                    context.setLastPart(part);
                }
                LinkedList<String> finalTooltip = new LinkedList<>();
                for (TooltipPart part : allParts) {
                    List<String> tooltip = part.getTooltips();
                    if (tooltip != null) {
                        finalTooltip.addAll(tooltip);
                    }

                }
                return finalTooltip;
            }
        };
    }
}
