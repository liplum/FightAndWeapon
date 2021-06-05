package net.liplum.gui.client;

import net.liplum.gui.server.MasterContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MasterGui extends GuiContainer {
    public MasterGui() {
        super(new MasterContainer());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        Logger logger = LogManager.getLogger();
        logger.info("Open gui successfully!");
    }
}
