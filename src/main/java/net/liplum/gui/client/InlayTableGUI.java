package net.liplum.gui.client;

import net.liplum.I18ns;
import net.liplum.Resources;
import net.liplum.Vanilla;
import net.liplum.gui.server.InlayTableContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class InlayTableGUI extends GuiContainer {
    private static final int XSize = 176;
    private static final int YSize = 166;
    private static final ResourceLocation Texture =
            Resources.genGuiContainerTx(Resources.Textures.GUI.InlayTable);

    public InlayTableGUI(EntityPlayer player, World world, int x, int y, int z) {
        super(new InlayTableContainer(player, world, x, y, z));
        xSize = XSize;
        ySize = YSize;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        super.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        //Center the GUI
        int left = (width - xSize) / 2;
        int top = (height - ySize) / 2;
        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.getTextureManager().bindTexture(Texture);
        drawTexturedModalRect(left, top, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String blockName = I18n.format(I18ns.Tile.InlayTable_Name);
        fontRenderer.drawString(blockName, xSize / 2, 6, Vanilla.Color.BlackColor);
    }
}
