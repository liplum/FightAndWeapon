package net.liplum.items;


import net.liplum.api.weapon.IGemstone;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

public class GemstoneItem extends Item {
    private final IGemstone gemstone;

    public GemstoneItem(@Nonnull IGemstone gemstone) {
        this.gemstone = gemstone;
        this.maxStackSize = gemstone.getQuality().getMaxItemStackSize();
    }

    public IGemstone getGemstone() {
        return gemstone;
    }
}
