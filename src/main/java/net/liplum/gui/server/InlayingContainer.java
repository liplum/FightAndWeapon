package net.liplum.gui.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InlayingContainer extends Container {
    private static final double MaxReachDistanceSq = 64;
    private final World world;
    private final BlockPos pos;

    public InlayingContainer(EntityPlayer player, World world, int x, int y, int z) {
        this.world = world;
        this.pos = new BlockPos(x, y, z);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.world.equals(world) &&
                playerIn.getDistanceSq(pos) <= MaxReachDistanceSq;
    }
}
