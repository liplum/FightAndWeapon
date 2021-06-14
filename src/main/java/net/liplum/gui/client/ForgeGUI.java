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
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.GuiScrollingList;

import java.util.LinkedList;

public class ForgeGUI extends GuiContainer {
    public static final int AdditionIconXOffset = 107;
    public static final int AdditionIconYOffset = 41;
    public static final int MaterialIconXOffset = 107;
    public static final int MaterialIconYOffset = 95;
    private static final int XSize = 175;
    private static final int YSize = 221;
    private static final ResourceLocation Texture =
            Resources.genGuiContainerTx(Resources.Textures.GUI.Forge);
    private static final IView ForgeTexture =
            GuiUtil.Full255View.slice(XSize, YSize);
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
    private static final IView[] Icons = new IView[IconCount];
    private static final IView AdditionIconTexture;
    private static final IView MaterialIconTexture;

    static {
        for (int i = 0; i < IconCount; i++) {
            Icons[i] = IconsTexture.slice(
                    i * (IconLength + IconBoardThickness) + IconBoardThickness,
                    IconBoardThickness,
                    IconLength,
                    IconLength
            );
        }
        AdditionIconTexture = Icons[0];
        MaterialIconTexture = Icons[1];
    }

    private CastScrollView castScrollView;

    private final int CastLength = 18;

    private final LinkedList<Cast> castList = new LinkedList<>();
    private int selectedIndex;

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
    public void initGui() {
        super.initGui();
    }

    public void selectCast(int index) {
        if (index == this.selectedIndex) {
            return;
        }
        this.selectedIndex = index;
        //TODO:
    }

    public boolean isSelected(int index) {
        return index == selectedIndex;
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

    private class CastScrollView extends GuiScrollingList {

        public CastScrollView(int width, int height, int top, int bottom, int left, int entryHeight, int screenWidth, int screenHeight) {
            super(ForgeGUI.this.mc, width, height, top, bottom, left, entryHeight, screenWidth, screenHeight);
        }

        @Override
        protected int getSize() {
            return castList.size();
        }

        @Override
        protected void elementClicked(int index, boolean doubleClick) {

        }

        @Override
        protected boolean isSelected(int index) {
            return ForgeGUI.this.isSelected(index);
        }

        @Override
        protected void drawBackground() {
            drawDefaultBackground();
        }

        @Override
        protected int getContentHeight() {
            return getSize() * CastLength;
        }

        @Override
        protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {

        }
    }
}
