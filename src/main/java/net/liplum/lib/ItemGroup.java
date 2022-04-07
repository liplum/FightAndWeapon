package net.liplum.lib;

import net.liplum.api.annotations.LongSupport;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@LongSupport
public class ItemGroup extends CreativeTabs {
    @NotNull
    private ItemStack icon = ItemStack.EMPTY;

    @LongSupport
    public ItemGroup(@NotNull String registerName) {
        super(registerName);
    }

    @LongSupport
    public boolean setIcon(@NotNull ItemStack newIcon) {
        if (newIcon.isEmpty() && newIcon == this.icon) {
            return false;
        }
        this.icon = newIcon;
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void displayAllRelevantItems(@NotNull NonNullList<ItemStack> currentItemGroupItems) {
        super.displayAllRelevantItems(currentItemGroupItems);
        sortDisplayedItems(currentItemGroupItems);
    }

    @SideOnly(Side.CLIENT)
    protected void sortDisplayedItems(@NotNull NonNullList<ItemStack> currentItemGroupItems) {

    }

    @NotNull
    @Override
    public ItemStack getTabIconItem() {
        return icon;
    }
}
