package net.liplum;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.liplum.lib.items.weaponutils.HarpUtils;

public final class Tags {
    public static final int Type_String = (new NBTTagString()).getId();
    public static final int Type_NbtCompound = (new NBTTagCompound()).getId();

    /*
    FAWData(Base)-str
        Gemstones(GemstoneList)-list(str)
            [
                {
                    Gemstone(Gemstone)-str
                    Gemergy(Gemergy)-int
                },
                ...
            ]
        Durability(Durability)-int
    */
    private Tags() {
    }

    public static final String Base = "FAWData";

    public final static class BaseSub {
        public static final String GemstoneList = "GemstoneList";

        public static final class GemstoneObject {
            public static final String Gemstone = "Gemstone";
            public static final String Gemergy = "Gemergy";
        }

        //Unbreaking
        public static final String Durability = "Durability";
    }
    public final static class Harp{

        /**
         * {@link HarpUtils.HarpState}
         */
        public static final String HarpState = "HarpState";
    }
}
