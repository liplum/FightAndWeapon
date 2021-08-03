package net.liplum.items;


import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.IGemstoneItem;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

public class GemstoneItem extends Item implements IGemstoneItem {
    @Nonnull
    private final IGemstone gemstone;

    public GemstoneItem(@Nonnull IGemstone gemstone) {
        this.gemstone = gemstone;
        this.maxStackSize = gemstone.getQuality().getMaxItemStackSize();
    }

    @Nonnull
    public IGemstone getGemstone() {
        return gemstone;
    }
}
