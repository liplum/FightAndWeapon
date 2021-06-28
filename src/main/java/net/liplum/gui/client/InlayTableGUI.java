package net.liplum.gui.client;

import net.liplum.I18ns;
import net.liplum.Resources;
import net.liplum.Vanilla;
import net.liplum.gui.server.InlayTableContainer;
import net.liplum.lib.gui.*;
import net.liplum.lib.utils.GuiUtil;
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
    private static final View InlayTableTexture = GuiUtil.Full255View.slice(XSize, YSize);
    private static final View CrossView = GuiUtil.Full255View.slice(176, 0, 17, 18);
    private static final TextureFactory textureFactory = new TextureFactory(Texture);
    private static final Texture Cross = textureFactory.gen(CrossView);
    private static final int CrossXOffset = 55;
    private static final int CrossYOffset = 35;

    private final InlayTableContainer inlayTable;
    private final Property<Boolean> displayCross = new Property<>(false);
    private final Binding<Boolean> displayCrossBinding;

    public InlayTableGUI(EntityPlayer player, World world, int x, int y, int z) {
        super(new InlayTableContainer(player, world, x, y, z));
        this.inlayTable = (InlayTableContainer) this.inventorySlots;
        this.xSize = XSize;
        this.ySize = YSize;
        this.displayCrossBinding = new Binding<>(inlayTable.getCanDo(), displayCross, BindingMode.ONLY_GET, v -> !v);
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
        drawTexturedModalRect(left, top,
                InlayTableTexture.getLeft(), InlayTableTexture.getTop(), InlayTableTexture.getWidth(), InlayTableTexture.getHeight());

        if (Utils.notNull(displayCross.get())) {
            Cross.draw(this, left + CrossXOffset, top + CrossYOffset);
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
