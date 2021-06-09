package net.liplum.gui;

import net.liplum.gui.client.MasterGui;
import net.liplum.gui.server.MasterContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class FawGuiHandler implements IGuiHandler {
    private static int Number = 1;

    public static int Master_ID = Number++;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Master_ID) {
            return new MasterContainer();
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Master_ID) {
            return new MasterGui();
        }
        return null;
    }
}