package net.liplum.gui;

import net.liplum.gui.client.ForgeGUI;
import net.liplum.gui.client.InlayTableGUI;
import net.liplum.gui.client.MasteryGui;
import net.liplum.gui.server.ForgeContainer;
import net.liplum.gui.server.InlayTableContainer;
import net.liplum.gui.server.MasteryContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class FawGuiHandler implements IGuiHandler {
    private static int Number = 1;

    public static int Mastery_ID = Number++;
    public static int Inlay_Table_ID = Number++;
    public static int Forge_ID = Number++;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Mastery_ID) {
            return new MasteryContainer(player, world, x, y, z);
        } else if (ID == Inlay_Table_ID) {
            return new InlayTableContainer(player, world, x, y, z);
        } else if (ID == Forge_ID) {
            return new ForgeContainer(player, world, x, y, z);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Mastery_ID) {
            return new MasteryGui(player, world, x, y, z);
        } else if (ID == Inlay_Table_ID) {
            return new InlayTableGUI(player, world, x, y, z);
        } else if (ID == Forge_ID) {
            return new ForgeGUI(player, world, x, y, z);
        }
        return null;
    }
}
