package net.liplum.items.gemstones;


import net.liplum.api.weapon.IGemstone;
import net.minecraft.item.Item;

public class GemstoneItem extends Item {
    private IGemstone gemstone;

    public GemstoneItem(IGemstone gemstone) {
        this.gemstone = gemstone;
    }

    public IGemstone getGemstone() {
        return gemstone;
    }
}
