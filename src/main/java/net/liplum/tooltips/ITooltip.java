package net.liplum.tooltips;

import javax.annotation.Nonnull;
import java.util.List;

public interface ITooltip {
    @Nonnull
    List<String> getTooltip();
}
