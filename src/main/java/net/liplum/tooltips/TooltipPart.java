package net.liplum.tooltips;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TooltipPart {
    @Nullable
    private List<String> tooltips;
    @Nonnull
    private final List<TooltipPart> allNodes = new ArrayList<>();

    public TooltipPart(@Nonnull List<String> tooltips) {
        this.tooltips = tooltips;
    }

    public TooltipPart() {
    }

    public static TooltipPart head() {
        return new TooltipPart();
    }

    @Nonnull
    public TooltipPart add(@Nonnull TooltipPart other) {
        this.allNodes.addAll(other.allNodes);
        return this;
    }

    @Nonnull
    public List<String> merge() {
        LinkedList<String> res = new LinkedList<>();
        for (TooltipPart part : allNodes) {
            if (part.tooltips != null) {
                res.addAll(part.tooltips);
            }
        }
        return res;
    }
}
