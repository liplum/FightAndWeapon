package net.liplum;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = MetaData.MOD_ID,name = MetaData.MOD_NAME,version = MetaData.MOD_VERSION)
public class FawMod {

    private static final Logger LOGGER = LogManager.getLogger();

    @Mod.Instance
    public static FawMod instance;

    public FawMod() {

    }
}
