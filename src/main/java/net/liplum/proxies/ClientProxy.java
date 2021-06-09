package net.liplum.proxies;

import net.liplum.registeies.HotkeyRegistry;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientProxy extends ProxyBase {
    private static final Logger Logger = LogManager.getLogger();

    public static void initHotkeys() {
        for (KeyBinding hotkey : HotkeyRegistry.getAllHotkeys()) {
            ClientRegistry.registerKeyBinding(hotkey);
        }
        Logger.info("Hotkey Component initialized successfully.");
    }

    @Override
    public boolean isPhysicalServer() {
        return false;
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        initHotkeys();
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }
}
