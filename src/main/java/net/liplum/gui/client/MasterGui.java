package net.liplum.gui.client;

import net.liplum.Resources;
import net.liplum.gui.server.MasterContainer;
import net.liplum.lib.gui.IView;
import net.liplum.lib.utils.GuiUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MasterGui extends GuiContainer {
    private static final ResourceLocation Texture =
            Resources.genGuiContainerTx(Resources.Textures.GUI.Master);
    private static final IView ArrowButtonsTx = GuiUtil.Full255View.slice(36, 30);
    private static final int ArrowButtonWidth = 12;
    private static final int ArrowButtonHeight = 15;
    private static final int ArrowButtonsRow = 2;
    private static final int ArrowButtonsColumn = 3;
    private static final IView[][] ArrowButtons = new IView[ArrowButtonsRow][ArrowButtonsColumn];
    private static final IView R_Normal;
    private static final IView R_Over;
    private static final IView R_Pressed;
    private static final IView L_Normal;
    private static final IView L_Over;
    private static final IView L_Pressed;

    static {
        for (int row = 0; row < ArrowButtonsRow; row++) {
            for (int column = 0; column < ArrowButtonsColumn; column++) {
                ArrowButtons[row][column] = ArrowButtonsTx.slice(
                        row * ArrowButtonWidth,
                        column * ArrowButtonHeight,
                        ArrowButtonWidth,
                        ArrowButtonHeight
                );
            }
        }
        R_Normal = ArrowButtons[0][0];
        R_Over = ArrowButtons[0][1];
        R_Pressed = ArrowButtons[0][2];
        L_Normal = ArrowButtons[1][0];
        L_Over = ArrowButtons[1][1];
        L_Pressed = ArrowButtons[1][2];
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    public MasterGui(EntityPlayer player, World world, int x, int y, int z) {
        super(new MasterContainer(player, world, x, y, z));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        Logger logger = LogManager.getLogger();
        logger.info("Open gui successfully!");
    }

    public class TurnWeaponPageButton extends GuiButton {

        public TurnWeaponPageButton(int buttonId, int x, int y, String buttonText) {
            super(buttonId, x, y, buttonText);
            this.width = ArrowButtonWidth;
            this.height = ArrowButtonHeight;
        }
    }
}
