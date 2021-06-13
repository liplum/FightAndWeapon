package net.liplum.gui.server;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public abstract class ContainerBase extends Container {

    protected void addPlayerInventorySlots(InventoryPlayer playerInventory, int left, int top) {
        for (int column = 0; column < 9; column++) {
            for (int row = 0; row < 3; row++) {
                addSlotToContainer(new Slot(playerInventory,
                        column + 9 * (row + 1),
                        left + 18 * column,
                        top + 18 * row));
            }
            addSlotToContainer(new Slot(playerInventory,
                    column,
                    left + 18 * column,
                    top + 58));
        }
    }

}
