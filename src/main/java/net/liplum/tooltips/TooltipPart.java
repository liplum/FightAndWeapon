package net.liplum.tooltips;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class TooltipPart {
    @NotNull
    private final LinkedList<String> tooltips = new LinkedList<>();

    public TooltipPart(@NotNull List<String> tooltips) {
        this.tooltips.addAll(tooltips);
    }

    public TooltipPart(@NotNull String tooltip) {
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

    @NotNull
    public List<String> getTooltips() {
        return tooltips;
    }

    @NotNull
    public TooltipPart add(@NotNull List<String> content) {
        tooltips.addAll(content);
        return this;
    }

    @NotNull
    public TooltipPart add(@NotNull String content) {
        tooltips.add(content);
        return this;
    }

    @NotNull
    public TooltipPart add(@NotNull TooltipPart other) {
        List<String> otherTooltips = other.getTooltips();
        return this.add(otherTooltips);
    }

    @NotNull
    public TooltipPart addHead(@NotNull String content) {
        tooltips.addFirst(content);
        return this;
    }

    @NotNull
    public TooltipPart addHead(@NotNull List<String> contents) {
        tooltips.addAll(0, contents);
        return this;
    }


    @NotNull
    public TooltipPart addHead(@NotNull TooltipPart other) {
        List<String> otherTooltips = other.getTooltips();
        return this.addHead(otherTooltips);
    }
}
