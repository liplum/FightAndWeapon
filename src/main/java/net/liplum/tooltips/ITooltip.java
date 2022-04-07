package net.liplum.tooltips;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@FunctionalInterface
public interface ITooltip {
    @NotNull
    List<String> getTooltip();
}
