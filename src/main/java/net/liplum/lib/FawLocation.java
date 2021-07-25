package net.liplum.lib;

import net.liplum.MetaData;
import net.liplum.api.annotations.LongSupport;
import net.minecraft.util.ResourceLocation;

@LongSupport
public class FawLocation extends ResourceLocation {

    @LongSupport
    public FawLocation(String resourcePathIn) {
        super(MetaData.MOD_ID, resourcePathIn);
    }
}
