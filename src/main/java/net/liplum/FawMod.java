package net.liplum;

import net.liplum.commands.InlayCommand;
import net.liplum.lib.utils.MasterUtil;
import net.liplum.proxies.ProxyBase;
import net.liplum.registeies.CapabilityRegistry;
import net.liplum.registeies.FawNetworkRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = MetaData.MOD_ID, name = MetaData.MOD_NAME, version = MetaData.MOD_VERSION)
public class FawMod {

    private static final Logger logger = LogManager.getLogger();

    @SidedProxy(clientSide = MetaData.CLIENT_PROXY_CLZ,
            serverSide = MetaData.SERVER_PROXY_CLZ)
    public static ProxyBase proxy;

    @Mod.Instance
    public static FawMod instance;

    public FawMod() {

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CapabilityRegistry.init();
        logger.info("Capability Component initialized successfully.");
        FawNetworkRegistry.init();
        logger.info("Network Component initialized successfully.");
        proxy.preInit(event);
        logger.info("Proxy Component pre-initialized successfully.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Gemstones.load();
        logger.info("Gemstone Component loaded successfully.");
        MasterUtil.init();
        proxy.init(event);
        logger.info("Proxy Component initialized successfully.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        logger.info("Proxy Component post-initialized successfully.");
    }

    @Mod.EventHandler
    public void postInit(FMLServerStartingEvent event) {
        event.registerServerCommand(new InlayCommand());
        logger.info("Command Component loaded successfully.");
    }
}
