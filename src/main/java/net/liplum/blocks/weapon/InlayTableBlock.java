package net.liplum.blocks.weapon;

import net.liplum.MetaData;
import net.liplum.gui.FawGuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class InlayTableBlock extends Block {
    public InlayTableBlock() {
        super(Material.WOOD);
    }

    @Override
    public boolean onBlockActivated(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state,
                                    @Nonnull EntityPlayer playerIn,
                                    @Nonnull EnumHand hand, @Nonnull EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(MetaData.MOD_ID,
                    FawGuiHandler.Inlay_Table_ID,
                    worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
}
