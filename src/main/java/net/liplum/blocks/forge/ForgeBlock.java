package net.liplum.blocks.forge;

import net.liplum.MetaData;
import net.liplum.gui.FawGuiHandler;
import net.liplum.tileentities.forge.ForgeTE;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ForgeBlock extends BlockContainer {
    public ForgeBlock() {
        super(Material.IRON);
    }

    @Override
    public boolean hasTileEntity(@NotNull IBlockState state) {
        return true;
    }

    @Override
    public boolean onBlockActivated(World worldIn, @NotNull BlockPos pos,
                                    @NotNull IBlockState state,
                                    @NotNull EntityPlayer playerIn,
                                    @NotNull EnumHand hand,
                                    @NotNull EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof ForgeTE) {
                playerIn.openGui(MetaData.MOD_ID, FawGuiHandler.Forge_ID,
                        worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@NotNull World worldIn, int meta) {
        return new ForgeTE();
    }

    @NotNull
    @Override
    public EnumBlockRenderType getRenderType(@NotNull IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
}
