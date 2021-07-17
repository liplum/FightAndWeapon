package net.liplum.tooltips;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class TooltipPart {
    public static final TooltipPart Empty = new TooltipPart() {
        @Override
        public TooltipPart add(String content) {
            return this;
        }

        @Override
        public TooltipPart addHead(String content) {
            return this;
        }
    };

    @Nullable
    private LinkedList<String> tooltips;

    public boolean hasAnyTooltip() {
        return tooltips != null && tooltips.size() > 0;
    }

    public TooltipPart(@Nonnull LinkedList<String> tooltips) {
        this.tooltips = tooltips;
    }

    public TooltipPart() {
    }

    @Nullable
    public List<String> getTooltips() {
        return tooltips;
    }

    public TooltipPart add(String content) {
        if (tooltips == null) {
            tooltips = new LinkedList<>();
        }
        tooltips.add(content);
        return this;
    }

    public TooltipPart addHead(String content) {
        if (tooltips == null) {
            tooltips = new LinkedList<>();
        }
        tooltips.addFirst(content);
        return this;
    }
}
