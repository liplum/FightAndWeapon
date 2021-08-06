package net.liplum;

import net.liplum.api.annotations.LongSupport;
import net.liplum.lib.utils.MasteryUtil;
import net.liplum.proxies.ProxyBase;
import net.liplum.registries.CapabilityRegistry;
import net.liplum.registries.CommandRegistry;
import net.liplum.registries.FawNetworkRegistry;
import net.liplum.skills.mastery.MasteryPassiveSkills;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = MetaData.MOD_ID, name = MetaData.MOD_NAME, version = MetaData.MOD_VERSION)
@LongSupport
public class FawMod {

    public static final Logger Logger = LogManager.getLogger();

    @SidedProxy(clientSide = MetaData.CLIENT_PROXY_CLZ,
            serverSide = MetaData.SERVER_PROXY_CLZ)
    public static ProxyBase Proxy;

    @Mod.Instance
    public static FawMod Instance;

    public FawMod() {

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CapabilityRegistry.init();
        Logger.info("Capability Component initialized successfully.");
        FawNetworkRegistry.init();
        Logger.info("Network Component initialized successfully.");
        Attributes.load();
        Logger.info("Attribute Component initialized successfully.");
        WeaponTypes.load();
        Logger.info("Weapon Type Component initialized successfully.");
        WeaponParts.load();
        Logger.info("Weapon Part Component initialized successfully.");
        MasteryPassiveSkills.load();
        Logger.info("Mastery Passive Skill Component initialized successfully.");
        Masteries.load();
        Logger.info("Mastery Component initialized successfully.");
        Proxy.preInit(event);
        Logger.info("Proxy Component pre-initialized successfully.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Gemstones.load();
        Logger.info("Gemstone Component loaded successfully.");
        MasteryUtil.init();
        BehaviorHandlers.init();
        Proxy.init(event);
        Logger.info("Proxy Component initialized successfully.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Proxy.postInit(event);
        Logger.info("Proxy Component post-initialized successfully.");
    }

    @Mod.EventHandler
    public void postInit(FMLServerStartingEvent event) {
        CommandRegistry.load(event);
        Logger.info("Command Component loaded successfully.");
    }
}
