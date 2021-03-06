package net.liplum.lib.utils;

import net.liplum.lib.gui.View;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public final class GuiUtil {

    public static final View Full255View = new View(255, 255);
    public static final View Full16View = new View(16, 16);

    public static void setEmpty(Slot slot) {
        slot.putStack(ItemStack.EMPTY);
    }

    public static boolean ifEmptyThenPut(Slot slot, ItemStack itemStack) {
        if (!slot.getHasStack()) {
            slot.putStack(itemStack);
            return true;
        }
        return false;
    }
}
