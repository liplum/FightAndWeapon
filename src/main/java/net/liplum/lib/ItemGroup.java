package net.liplum.lib;

import net.liplum.api.annotations.LongSupport;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@LongSupport
public class ItemGroup extends CreativeTabs {
    @Nonnull
    private ItemStack icon = ItemStack.EMPTY;

    @LongSupport
    public ItemGroup(@Nonnull String registerName) {
        super(registerName);
    }

    @LongSupport
    public boolean setIcon(@Nonnull ItemStack newIcon) {
        if (newIcon.isEmpty()) {
            return false;
        }
        this.icon = newIcon;
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getTabIconItem() {
        return icon;
    }
}
