package net.liplum.tooltips;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.LinkedList;

public class AggregateThroughable implements IThroughable {
    @NotNull
    private final LinkedList<IThroughable> allAggregated = new LinkedList<>();

    public AggregateThroughable(IThroughable... throughables) {
        Collections.addAll(allAggregated, throughables);
    }

    public AggregateThroughable aggregate(IThroughable... throughables) {
        Collections.addAll(allAggregated, throughables);
        return this;
    }

    @NotNull
    @Override
    public TooltipPart through(@NotNull IPipeContext context) {
        TooltipPart res = new TooltipPart();
        for (IThroughable sub : allAggregated) {
            res.add(sub.through(context));
        }
        return res;
    }
}
