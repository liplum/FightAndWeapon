package net.liplum.items;

import net.liplum.api.registeies.CastRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class CastItem extends Item {
    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
        for (String name : CastRegistry.getAllCastNames()) {
            ItemStack itemStack = new ItemStack(this);
            items.add(itemStack);
        }
    }
}
