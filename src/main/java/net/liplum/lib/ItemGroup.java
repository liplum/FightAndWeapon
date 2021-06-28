package net.liplum.lib;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemGroup extends CreativeTabs {
    private ItemStack icon;

    public ItemGroup(String registerName) {
        super(registerName);
        this.icon = ItemStack.EMPTY;
    }

    public boolean setIcon(ItemStack newIcon) {
        if (newIcon == null || newIcon.isEmpty()) {
            return false;
        }
        icon = newIcon;
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getTabIconItem() {
        return icon;
    }
}
