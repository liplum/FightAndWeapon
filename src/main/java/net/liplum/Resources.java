package net.liplum;

import net.liplum.lib.FawLocation;
import net.minecraft.util.ResourceLocation;

public final class Resources {
    public static final String PNG_Extension = ".png";
    public static final String JSON_Extension = ".json";
    public static final String Weapon_Extension = ".faw";

    public static ResourceLocation genGuiTx(String unsuffixedFileName) {
        String str = Vanilla.Resources.Textures + '/' +
                Vanilla.Resources.GUI + '/' +
                unsuffixedFileName +
                PNG_Extension;
        return new FawLocation(str);
    }

    public static ResourceLocation genGuiContainerTx(String unsuffixedFileName) {
        String str = Vanilla.Resources.Textures + '/' +
                Vanilla.Resources.GUI + '/' +
                Vanilla.Resources.Container + '/' +
                unsuffixedFileName +
                PNG_Extension;
        return new FawLocation(str);
    }

    public static final class Textures {
        public static final class GUI {
            public static final String InlayTable = Names.Block.InlayTable;
            public static final String Forge = Names.Block.Forge;
            public static final String Master = Names.Special.Mastery;
        }
    }
}
