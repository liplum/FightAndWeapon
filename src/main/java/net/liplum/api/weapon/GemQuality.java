package net.liplum.api.weapon;

import org.jetbrains.annotations.NotNull;

public class GemQuality implements Comparable<GemQuality> {
    @NotNull
    public static final GemQuality None = new GemQuality(Integer.MIN_VALUE, 64);
    @NotNull
    public static final GemQuality Low = new GemQuality(-500, 16);
    @NotNull
    public static final GemQuality Middle = new GemQuality(0, 1);
    @NotNull
    public static final GemQuality High = new GemQuality(500, 1);
    private final int number;
    private final int maxItemStackSize;

    public GemQuality(int number, int maxItemStackSize) {
        this.number = number;
        this.maxItemStackSize = maxItemStackSize;
    }

    public int getNumber() {
        return number;
    }

    public int getMaxItemStackSize() {
        return maxItemStackSize;
    }

    @Override
    public int compareTo(GemQuality o) {
        if (this.number == o.number) {
            return 0;
        }
        return this.number < o.number ? -1 : 1;
    }
}
