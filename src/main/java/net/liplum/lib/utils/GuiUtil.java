package net.liplum.lib.utils;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public final class GuiUtil {
    public static final int PlayerInventoryCount = 36;

    public static void setEmpty(Slot slot) {
        slot.putStack(ItemStack.EMPTY);
    }
}
