package net.liplum.items.gemstones;


import net.liplum.api.weapon.IGemstone;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class GemstoneItem extends Item {
    private IGemstone gemstone;

    public GemstoneItem(IGemstone gemstone) {
        this.gemstone = gemstone;
    }

    public IGemstone getGemstone() {
        return gemstone;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        super.getSubItems(tab, items);
    }
}
