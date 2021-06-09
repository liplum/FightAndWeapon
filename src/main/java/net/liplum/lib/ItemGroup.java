package net.liplum.lib;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemGroup extends CreativeTabs {
    private ItemStack icon;

    public ItemGroup(String registerName) {
        super(registerName);
        this.icon = new ItemStack(Items.AIR);
    }

    public boolean setIcon(ItemStack newIcon) {
        if (newIcon == null || newIcon.isEmpty()) {
            return false;
        }
        icon = newIcon;
        return true;
    }

    @Override
    public ItemStack getTabIconItem() {
        return icon;
    }
}
