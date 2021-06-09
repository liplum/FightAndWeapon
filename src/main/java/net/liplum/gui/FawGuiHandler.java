package net.liplum.gui;

import net.liplum.gui.client.InlayTableGUI;
import net.liplum.gui.client.MasterGui;
import net.liplum.gui.server.InlayTableContainer;
import net.liplum.gui.server.MasterContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class FawGuiHandler implements IGuiHandler {
    private static int Number = 1;

    public static int Master_ID = Number++;
    public static int Inlay_Table_ID = Number++;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Master_ID) {
            return new MasterContainer();
        } else if (ID == Inlay_Table_ID) {
            return new InlayTableContainer(player, world, x, y, z);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Master_ID) {
            return new MasterGui();
        } else if (ID == Inlay_Table_ID) {
            return new InlayTableGUI(player, world, x, y, z);
        }
        return null;
    }
}
