package net.liplum;

import net.minecraft.nbt.NBTTagString;

public final class Tags {
    public static final int Type_String = (new NBTTagString()).getId();

    /*
    FAWData(Base)-str
        Modifiers(ModifierList)-list(str)
            [
                {
                    Modifier(Modifier)-str
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
        public static final String ModifierList = "ModifierList";

        public static final class ModifierObject {
            public static final String Modifier = "Modifier";
            public static final String Gemergy = "Gemergy";
        }

        //Unbreaking
        public static final String Durability = "Durability";
    }
}
