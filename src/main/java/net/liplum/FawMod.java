package net.liplum;

import net.liplum.lib.utils.MasterUtil;
import net.liplum.registeies.CapabilityRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = MetaData.MOD_ID, name = MetaData.MOD_NAME, version = MetaData.MOD_VERSION)
public class FawMod {

    private static final Logger logger = LogManager.getLogger();

    @Mod.Instance
    public static FawMod instance;

    public FawMod() {

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CapabilityRegistry.load();
        logger.info("Capabilities loaded successfully.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Gemstones.load();
        logger.info("Gemstones loaded successfully.");
        MasterUtil.init();
    }
}
