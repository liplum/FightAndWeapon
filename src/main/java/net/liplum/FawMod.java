package net.liplum;

import net.liplum.lib.utils.MasterUtil;
import net.liplum.proxies.ProxyBase;
import net.liplum.registeies.CapabilityRegistry;
import net.liplum.registeies.FawNetworkRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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
        proxy.init();
        logger.info("Proxy Component initialized successfully.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Gemstones.load();
        logger.info("Gemstone Component loaded successfully.");
        MasterUtil.init();
    }
}
