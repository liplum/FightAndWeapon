package net.liplum.lib;

import net.liplum.MetaData;
import net.minecraft.util.ResourceLocation;

public class FawLocation extends ResourceLocation {

    public FawLocation(String resourcePathIn) {
        super(MetaData.MOD_ID, resourcePathIn);
    }
}
