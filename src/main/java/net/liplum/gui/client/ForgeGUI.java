package net.liplum.gui.client;

import net.liplum.I18ns;
import net.liplum.Resources;
import net.liplum.Vanilla;
import net.liplum.gui.server.ForgeContainer;
import net.liplum.lib.gui.Binding;
import net.liplum.lib.gui.BindingMode;
import net.liplum.lib.gui.IView;
import net.liplum.lib.gui.Property;
import net.liplum.lib.utils.GuiUtil;
import net.liplum.lib.utils.Utils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ForgeGUI extends GuiContainer {
    public static final int AdditionIconXOffset = 107;
    public static final int AdditionIconYOffset = 41;
    public static final int MaterialIconXOffset = 107;
    public static final int MaterialIconYOffset = 95;
    private static final int XSize = 175;
    private static final int YSize = 221;
    private static final ResourceLocation Texture =
            Resources.genGuiContainerTx(Resources.Textures.GUI.Forge);
    private static final IView ForgeTexture = GuiUtil.Full255View.slice(XSize, YSize);
    private static final int IconBoardThickness = 1;
    private static final int IconLength = 18;
    private static final int IconCount = 2;
    /**
     * Icon list(from left to right):
     * AdditionSlot,MaterialSlot
     */
    private static final IView IconsTexture = GuiUtil.Full255View.slice(
            176, 0,
            IconCount * IconLength + IconBoardThickness * (IconCount + 1),
            IconLength + IconCount * IconBoardThickness);
    private static final IView[] Icons = new IView[2];
    private static final IView AdditionIconTexture = Icons[0];
    private static final IView MaterialIconTexture = Icons[1];

    static {
        for (int i = 0; i < IconCount; i++) {
            Icons[i] = IconsTexture.slice(
                    i * (IconLength + IconBoardThickness) + IconBoardThickness,
                    IconBoardThickness,
                    IconLength,
                    IconLength
            );
        }
    }

    private final ForgeContainer forge;
    private final Property<Boolean> displayAdditionIcon = new Property<>(true);
    private final Property<Boolean> displayMaterialIcon = new Property<>(true);
    private final Binding<Boolean> displayAdditionIconBinding;
    private final Binding<Boolean> displayMaterialIconBinding;

    public ForgeGUI(EntityPlayer player, World world, int x, int y, int z) {
        super(new ForgeContainer(player, world, x, y, z));
        this.forge = (ForgeContainer) this.inventorySlots;
        this.xSize = XSize;
        this.ySize = YSize;
        this.displayAdditionIconBinding = new Binding<>(forge.getAdditionSlotHasItem(), displayAdditionIcon, BindingMode.ONLY_GET, v -> !v);
        this.displayMaterialIconBinding = new Binding<>(forge.getMaterialSlotHasItem(), displayMaterialIcon, BindingMode.ONLY_GET, v -> !v);
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
                ForgeTexture.getLeft(), ForgeTexture.getTop(), ForgeTexture.getWidth(), ForgeTexture.getHeight());

        if (Utils.notNull(displayAdditionIcon.get())) {
            drawTexturedModalRect(left + AdditionIconXOffset, top + AdditionIconYOffset,
                    AdditionIconTexture.getLeft(), AdditionIconTexture.getTop(),
                    AdditionIconTexture.getWidth(), AdditionIconTexture.getHeight());
        }
        if (Utils.notNull(displayMaterialIcon.get())) {
            drawTexturedModalRect(left + MaterialIconXOffset, top + MaterialIconYOffset,
                    MaterialIconTexture.getLeft(), MaterialIconTexture.getTop(),
                    MaterialIconTexture.getWidth(), MaterialIconTexture.getHeight());
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        String blockName = I18n.format(I18ns.Tile.Forge_Name);
        fontRenderer.drawString(blockName, xSize / 2, 6, Vanilla.Color.BlackColor);
    }
}
