package net.liplum;

public final class I18ns {
    public static String prefixUnloc(String name) {
        return String.format("%s.%s", MetaData.MOD_ID, name);
    }

    public static String prefixPotionUnloc(String name) {
        return String.format("potion.%s.%s", MetaData.MOD_ID, name);
    }

    public static String prefixHotkeyUnloc(String name) {
        return String.format("hotkey.%s.%s", MetaData.MOD_ID, name);
    }

    public static String prefixHotkeyCategoryUnloc(String name) {
        return String.format("hotkey.category.%s", name);
    }

    public static String prefixCommandUnloc(String name) {
        return String.format("command.%s.usage", name);
    }

    public static final class Hotkey {
        public static final String Faw_Category = prefixHotkeyCategoryUnloc("faw");

        public static final class Master {
            public static final String Master = prefixHotkeyUnloc("master");
        }
    }

    public static final class Command {
        public static final String Inlay = prefixCommandUnloc("inlay");
    }
}
