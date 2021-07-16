package net.liplum.tooltips;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TooltipPart {
    @Nullable
    private List<String> tooltips;

    public TooltipPart(@Nonnull List<String> tooltips) {
        this.tooltips = tooltips;
    }

    public TooltipPart() {
    }

    @Nullable
    public List<String> getTooltips() {
        return tooltips;
    }
}
