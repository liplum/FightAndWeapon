package net.liplum.registeies;

import net.liplum.I18ns;
import net.liplum.MetaData;
import net.liplum.Names;
import net.liplum.blocks.TestFuncBlock;
import net.liplum.blocks.forge.TestBlock;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedList;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public final class BlockRegistry {
    public static final LinkedList<Block> Blocks = new LinkedList<>();

    public static final Block TestBlock = forge(new TestBlock(), "test_block");
    public static final Block TestFuncBlock = forge(new TestFuncBlock(), "test_func_block");

    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> blocks = event.getRegistry();
        for (Block block : Blocks) {
            blocks.register(block);
        }
    }

    private static Block with(Block block, String name, CreativeTabs itemGroup) {
        Blocks.addLast(block);
        return Names.setRegisterName(block, name).
                setUnlocalizedName(I18ns.prefixUnloc(name)).
                setCreativeTab(itemGroup);
    }

    public static Block forge(Block block, String name) {
        return with(block, name, ItemGroupsRegistry.FawForges);
    }

}
