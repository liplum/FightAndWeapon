package net.liplum.tooltips;

import javax.annotation.Nonnull;
import java.util.List;

@FunctionalInterface
public interface ITooltip {
    @Nonnull
    List<String> getTooltip();
}
