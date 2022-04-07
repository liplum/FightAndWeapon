package net.liplum.items;


import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.IGemstoneItem;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

public class GemstoneItem extends Item implements IGemstoneItem {
    @NotNull
    private final IGemstone gemstone;

    public GemstoneItem(@NotNull IGemstone gemstone) {
        this.gemstone = gemstone;
        this.maxStackSize = gemstone.getQuality().getMaxItemStackSize();
    }

    @NotNull
    public IGemstone getGemstone() {
        return gemstone;
    }
}
