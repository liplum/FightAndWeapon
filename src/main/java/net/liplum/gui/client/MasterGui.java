package net.liplum.gui.client;

import net.liplum.Resources;
import net.liplum.gui.server.MasterContainer;
import net.liplum.lib.gui.IView;
import net.liplum.lib.gui.Texture;
import net.liplum.lib.gui.TextureFactory;
import net.liplum.lib.gui.controls.Button;
import net.liplum.lib.utils.GuiUtil;
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
    private static final TextureFactory textureFactory = new TextureFactory(Texture);
    private static final Texture R_Normal;
    private static final Texture R_Hovered;
    private static final Texture R_Pressed;
    private static final Texture L_Normal;
    private static final Texture L_Hovered;
    private static final Texture L_Pressed;

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
        R_Normal = textureFactory.gen(ArrowButtons[0][0]);
        R_Hovered = textureFactory.gen(ArrowButtons[0][1]);
        R_Pressed = textureFactory.gen(ArrowButtons[0][2]);
        L_Normal = textureFactory.gen(ArrowButtons[1][0]);
        L_Hovered = textureFactory.gen(ArrowButtons[1][1]);
        L_Pressed = textureFactory.gen(ArrowButtons[1][2]);
    }

    private TurnWeaponPageButton turnLeft;
    private TurnWeaponPageButton turnRight;

    @Override
    public void initGui() {
        super.initGui();
        turnLeft = new TurnWeaponPageButton(0, "");
        turnRight = new TurnWeaponPageButton(1, "");
        addButton(turnLeft);
        addButton(turnRight);
    }

    public MasterGui(EntityPlayer player, World world, int x, int y, int z) {
        super(new MasterContainer(player, world, x, y, z));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        Logger logger = LogManager.getLogger();
        logger.info("Open gui successfully!");
    }

    public class TurnWeaponPageButton extends Button {

        public TurnWeaponPageButton(int buttonId, String buttonText) {
            super(buttonId, buttonText);
            this.width = ArrowButtonWidth;
            this.height = ArrowButtonHeight;
        }
    }
}
