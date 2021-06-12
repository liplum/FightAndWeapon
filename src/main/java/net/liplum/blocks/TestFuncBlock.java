package net.liplum.blocks;

import net.liplum.items.gemstones.GemstoneItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TestFuncBlock extends Block {
    public TestFuncBlock() {
        super(Material.IRON);
    }

    @Override
    public boolean onBlockActivated(World worldIn,
                                    BlockPos pos,
                                    IBlockState state,
                                    EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        Item item = heldItem.getItem();
        if (item instanceof GemstoneItem) {
            playerIn.addItemStackToInventory(new ItemStack(Blocks.DIRT, 1));
        }
        return true;
    }
}
