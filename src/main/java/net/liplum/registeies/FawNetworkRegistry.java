package net.liplum.registeies;

import net.liplum.MetaData;
import net.liplum.gui.FawGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class FawNetworkRegistry {
    public static void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(MetaData.MOD_ID, new FawGuiHandler());
    }
}
