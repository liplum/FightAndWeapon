package net.liplum.gui.client;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public class MasterGui extends GuiContainer {
    public MasterGui(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }
}
