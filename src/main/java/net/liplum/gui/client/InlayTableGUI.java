package net.liplum.gui.client;

import net.liplum.I18ns;
import net.liplum.Resources;
import net.liplum.Vanilla;
import net.liplum.gui.server.InlayTableContainer;
import net.liplum.lib.gui.Binding;
import net.liplum.lib.gui.BindingMode;
import net.liplum.lib.gui.Property;
import net.liplum.lib.utils.Utils;
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
    private final InlayTableContainer inlayTable;
    private final Property<Boolean> displayCross = new Property<>(false);
    private final Binding<Boolean> displayCrossBinding;

    public InlayTableGUI(EntityPlayer player, World world, int x, int y, int z) {
        super(new InlayTableContainer(player, world, x, y, z));
        inlayTable = (InlayTableContainer) this.inventorySlots;
        xSize = XSize;
        ySize = YSize;
        displayCrossBinding = new Binding<>(inlayTable.getCanDo(), displayCross, BindingMode.ONLY_GET, v -> !v);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        super.renderHoveredToolTip(mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        //Center the GUI
        int left = (width - xSize) / 2;
        int top = (height - ySize) / 2;
        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.getTextureManager().bindTexture(Texture);
        drawTexturedModalRect(left, top, 0, 0, xSize, ySize);
        if (Utils.notNull(displayCross.get())) {
            drawTexturedModalRect(left+55,top+35,176,0,17,18);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        String blockName = I18n.format(I18ns.Tile.InlayTable_Name);
        fontRenderer.drawString(blockName, xSize / 2, 6, Vanilla.Color.BlackColor);
    }
}
