package net.liplum.gui.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class MasteryContainer extends ContainerBase {
    public MasteryContainer(EntityPlayer player, World world, int x, int y, int z) {
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
