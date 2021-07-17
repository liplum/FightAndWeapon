package net.liplum.tooltips;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.LinkedList;

public class AggregateThroughable implements IThroughable {
    @Nonnull
    private final LinkedList<IThroughable> allAggregated = new LinkedList<>();

    public AggregateThroughable(IThroughable... throughables) {
        Collections.addAll(allAggregated, throughables);
    }

    public AggregateThroughable aggregate(IThroughable... throughables) {
        Collections.addAll(allAggregated, throughables);
        return this;
    }

    @Nonnull
    @Override
    public TooltipPart through(@Nonnull IPipeContext context) {
        TooltipPart res = new TooltipPart();
        for (IThroughable sub : allAggregated) {
            res.add(sub.through(context));
        }
        return res;
    }
}
