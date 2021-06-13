package net.liplum;

import net.liplum.lib.FawLocation;
import net.minecraft.util.ResourceLocation;

public final class Resources {
    private static final String PNG_Suffix = ".png";

    public static ResourceLocation genGuiContainerTx(String unsuffixedFileName) {
        String str = Vanilla.Resources.Textures + '/' +
                Vanilla.Resources.GUI + '/' +
                Vanilla.Resources.Container + '/' +
                unsuffixedFileName +
                PNG_Suffix;
        return new FawLocation(str);
    }

    public static final class Textures {
        public static final class GUI {
            public static final String InlayTable = Names.Block.InlayTable;
            public static final String Forge = Names.Block.Forge;
        }
    }
}
