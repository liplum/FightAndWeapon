package net.liplum;

import net.liplum.registeies.BlockRegistry;
import net.liplum.registeies.ItemRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MetaData.MOD_ID)
public class FawMod {

    private static final Logger LOGGER = LogManager.getLogger();

    public FawMod() {
        ItemRegistry.ITEMS_LIST.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegistry.BLOCKS_LIST.register(FMLJavaModLoadingContext.get().getModEventBus());

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
}
