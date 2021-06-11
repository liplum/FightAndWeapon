package net.liplum.items.gemstones;


import net.liplum.api.weapon.IGemstone;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

public class GemstoneItem extends Item {
    private IGemstone gemstone;

    public GemstoneItem(@Nonnull IGemstone gemstone) {
        this.gemstone = gemstone;
        this.maxStackSize = 16;
    }

    public IGemstone getGemstone() {
        return gemstone;
    }
}
