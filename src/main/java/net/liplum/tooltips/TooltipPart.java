package net.liplum.tooltips;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public class TooltipPart {
    @Nonnull
    private final LinkedList<String> tooltips = new LinkedList<>();

    public TooltipPart(@Nonnull List<String> tooltips) {
        this.tooltips.addAll(tooltips);
    }

    public TooltipPart(@Nonnull String tooltip) {
        this.tooltips.add(tooltip);
    }

    public TooltipPart() {
    }

    public static TooltipPart singleSpaceLine() {
        return new TooltipPart("");
    }

    public static TooltipPart empty() {
        return new TooltipPart();
    }

    public boolean hasAnyTooltip() {
        return tooltips.size() > 0;
    }

    @Nonnull
    public List<String> getTooltips() {
        return tooltips;
    }

    @Nonnull
    public TooltipPart add(@Nonnull List<String> content) {
        tooltips.addAll(content);
        return this;
    }

    @Nonnull
    public TooltipPart add(@Nonnull String content) {
        tooltips.add(content);
        return this;
    }

    @Nonnull
    public TooltipPart add(@Nonnull TooltipPart other) {
        List<String> otherTooltips = other.getTooltips();
        return this.add(otherTooltips);
    }

    @Nonnull
    public TooltipPart addHead(@Nonnull String content) {
        tooltips.addFirst(content);
        return this;
    }

    @Nonnull
    public TooltipPart addHead(@Nonnull List<String> contents) {
        tooltips.addAll(0, contents);
        return this;
    }


    @Nonnull
    public TooltipPart addHead(@Nonnull TooltipPart other) {
        List<String> otherTooltips = other.getTooltips();
        return this.addHead(otherTooltips);
    }
}
