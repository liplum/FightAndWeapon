package net.liplum;

import net.liplum.lib.items.weaponutils.HarpUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;

public final class Tags {
    public static final int Type_String = (new NBTTagString()).getId();
    public static final int Type_NbtCompound = (new NBTTagCompound()).getId();
    public static final String Base = "FAWData";

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

    public final static class BaseSub {
        public static final String GemstoneList = "GemstoneList";
        //Unbreaking
        public static final String Durability = "Durability";

        public static final class GemstoneObject {
            public static final String Gemstone = "Gemstone";
            public static final String Gemergy = "Gemergy";
        }
    }


    public final static class Master {
        public static final String MasterList = "MasterList";

        public final static class MasterObject {
            public static final String Type = "Type";
            public static final String Level = "Level";
            public static final String Exp = "Exp";
        }
    }


    public final static class Harp {

        /**
         * {@link HarpUtils.HarpState}
         */
        public static final String HarpState = "HarpState";
    }
}
