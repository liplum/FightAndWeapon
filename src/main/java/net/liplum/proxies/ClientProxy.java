package net.liplum.proxies;

import net.liplum.eventhandlers.ClientHandler;
import net.liplum.eventhandlers.HotkeyHandler;
import net.liplum.registeies.HotkeyRegistry;
import net.liplum.renders.CastTextureCreator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
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
        //I changed it to static
        //MinecraftForge.EVENT_BUS.register(CastTextureCreator.getInstance());
        MinecraftForge.EVENT_BUS.register(new HotkeyHandler());
        MinecraftForge.EVENT_BUS.register(new ClientHandler());
    }

    @Override
    public void init(FMLInitializationEvent event) {
        IResourceManager resourceManager = Minecraft.getMinecraft().getResourceManager();
        IReloadableResourceManager reloadResourceManager = (IReloadableResourceManager) resourceManager;
        reloadResourceManager.registerReloadListener(CastTextureCreator.getInstance());

        //Register a custom model loader of weapons
        //   ModelLoaderRegistry.registerLoader(WeaponModelLoader.getInstance());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }
}
