package net.liplum.items.gemstones;


import net.liplum.api.weapon.IGemstone;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class GemstoneItem extends Item {
    private IGemstone gemstone;

    public GemstoneItem(@Nonnull IGemstone gemstone) {
        this.gemstone = gemstone;
    }

    public IGemstone getGemstone() {
        return gemstone;
    }
}
