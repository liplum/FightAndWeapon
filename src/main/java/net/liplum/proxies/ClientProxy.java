package net.liplum.proxies;

import net.liplum.registeies.HotkeyRegistry;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientProxy extends ProxyBase {
    private static final Logger logger = LogManager.getLogger();

    public static void initHotkeys() {
        for (KeyBinding hotkey : HotkeyRegistry.getAllHotkeys()) {
            ClientRegistry.registerKeyBinding(hotkey);
        }
        logger.info("Hotkey Component initialized successfully.");
    }

    @Override
    public boolean isServer() {
        return false;
    }

    @Override
    public void init() {
        initHotkeys();
    }
}
