package net.liplum.registeies;

import net.liplum.MetaData;
import net.minecraft.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistries {
    public static final DeferredRegister<Block> BLOCKS_LIST = DeferredRegister.create(ForgeRegistries.BLOCKS, MetaData.MOD_ID);
}
