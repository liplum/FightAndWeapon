package net.liplum.gui.client;

import net.liplum.gui.server.MasterContainer;
import net.minecraft.client.gui.inventory.GuiContainer;

public class MasterGui extends GuiContainer {
    public MasterGui() {
        super(new MasterContainer());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }
}
