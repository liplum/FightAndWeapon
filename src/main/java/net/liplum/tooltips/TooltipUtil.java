package net.liplum.tooltips;

import java.util.List;

public final class TooltipUtil {
    public static boolean followingHasTooltip(List<TooltipEntry> entries) {
        for (TooltipEntry entry : entries) {
            if (entry.result.hasAnyTooltip()) {
                return true;
            }
        }
        return false;
    }
}
