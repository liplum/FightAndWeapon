package net.liplum;

import net.liplum.api.annotations.LongSupport;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;

@LongSupport
public final class Tags {
    public static final int Type_String = (new NBTTagString()).getId();
    public static final int Type_NbtCompound = (new NBTTagCompound()).getId();
    public static final String Base = "FAWData";

    /*Weapon
    FAWData(Base)-str
        GemstoneList(GemstoneList)-list(str)
            [
                {
                    Gemstone(Gemstone)-str
                    Gemergy(Gemergy)-int
                },
                ...
            ]
        Durability(Durability)-int
    */

    /*CastBlueprint
    FAWData(Base)-str
        WeaponPart(WeaponPart)-str
     */
    private Tags() {
    }

    public final static class BaseSub {
        public static final String GemstoneList = "GemstoneList";
        //Unbreaking
        public static final String Durability = "Durability";

        public static final String WeaponPart = "WeaponPart";

        public static final class GemstoneObject {
            public static final String Gemstone = "Gemstone";
            public static final String Gemergy = "Gemergy";
        }
    }


    public final static class Mastery {
        public static final String MasteryList = "MasteryList";

        public final static class MasteryObject {
            public static final String Type = "Type";
            public static final String Level = "Level";
            public static final String Exp = "Exp";
        }
    }
}
