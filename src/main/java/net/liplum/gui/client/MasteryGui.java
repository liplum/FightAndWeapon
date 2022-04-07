package net.liplum.gui.client;

import net.liplum.Resources;
import net.liplum.gui.server.MasteryContainer;
import net.liplum.lib.gui.Texture;
import net.liplum.lib.gui.TextureFactory;
import net.liplum.lib.gui.View;
import net.liplum.lib.gui.controls.Button;
import net.liplum.lib.utils.GuiUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MasteryGui extends GuiContainer {
    private static final ResourceLocation Texture =
            Resources.genGuiTx(Resources.Textures.GUI.Master);
    private static final TextureFactory textureFactory = new TextureFactory(Texture);

    private static final int ArrowButtonWidth = 12;
    private static final int ArrowButtonHeight = 18;
    private static final int ArrowButtonsRow = 2;
    private static final int ArrowButtonsColumn = 3;

    private static final View ArrowButtonsView = GuiUtil.Full255View.slice(
            ArrowButtonWidth * ArrowButtonsColumn, ArrowButtonHeight * ArrowButtonsRow);
    private static final View[][] ArrowButtons = new View[ArrowButtonsRow][ArrowButtonsColumn];

    private static final int WeaponTypeButtonWidth = 20;
    private static final int WeaponTypeButtonHeight = ArrowButtonHeight;
    private static final View WeaponTypeButtonView = GuiUtil.Full255View.slice(
            ArrowButtonWidth * ArrowButtonsColumn, 0,
            WeaponTypeButtonWidth, WeaponTypeButtonHeight);

    private static final Texture R_Normal;
    private static final Texture R_Hovered;
    private static final Texture R_Pressed;
    private static final Texture L_Normal;
    private static final Texture L_Hovered;
    private static final Texture L_Pressed;

    private static final View[] WeaponTypeButtons = new View[2];
    private static final Texture WeaponTypeButton_Normal;
    private static final Texture WeaponTypeButton_Pressed;

    static {
        for (int row = 0; row < ArrowButtonsRow; row++) {
            for (int column = 0; column < ArrowButtonsColumn; column++) {
                ArrowButtons[row][column] = ArrowButtonsView.slice(
                        column * ArrowButtonWidth,
                        row * ArrowButtonHeight,
                        ArrowButtonWidth,
                        ArrowButtonHeight
                );
            }
        }
        for (int i = 0; i < 2; i++) {
            WeaponTypeButtons[i] = WeaponTypeButtonView.slice(
                    0,
                    i * WeaponTypeButtonHeight,
                    WeaponTypeButtonWidth,
                    WeaponTypeButtonHeight
            );
        }
        WeaponTypeButton_Normal = textureFactory.gen(WeaponTypeButtons[0]);
        WeaponTypeButton_Pressed = textureFactory.gen(WeaponTypeButtons[1]);

        R_Normal = textureFactory.gen(ArrowButtons[0][0]);
        R_Hovered = textureFactory.gen(ArrowButtons[0][1]);
        R_Pressed = textureFactory.gen(ArrowButtons[0][2]);

        L_Normal = textureFactory.gen(ArrowButtons[1][0]);
        L_Hovered = textureFactory.gen(ArrowButtons[1][1]);
        L_Pressed = textureFactory.gen(ArrowButtons[1][2]);
    }

    private final Button turnLeft = new Button(0) {
        @Override
        public void onTrigger() {

        }
    }.setTextures(L_Normal, L_Hovered, L_Pressed)
            .setPosition(10, 20);

    private final Button turnRight = new Button(1) {
        @Override
        public void onTrigger() {

        }
    }.setTextures(R_Normal, R_Hovered, R_Pressed)
            .setPosition(30, 20);

    public MasteryGui(EntityPlayer player, World world, int x, int y, int z) {
        super(new MasteryContainer(player, world, x, y, z));
    }

    @Override
    public void initGui() {
        super.initGui();
        addButton(turnLeft);
        addButton(turnRight);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        /*for (GuiButton button : buttonList) {
            if (button.isMouseOver()) {
                button.drawButtonForegroundLayer(mouseX, mouseY);
            }
        }*/
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    }

    @Override
    protected void actionPerformed(@NotNull GuiButton button) throws IOException {
        if (button instanceof Button) {
            Button b = (Button) button;
            b.onTrigger();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }
}
