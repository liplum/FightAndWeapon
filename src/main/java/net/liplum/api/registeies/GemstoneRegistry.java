package net.liplum.api.registeies;

import net.liplum.api.weapon.IGemstone;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GemstoneRegistry {
    private static final Map<String, IGemstone> GemstoneMap = new HashMap<>();
    private static ArrayList<String> NamesCache = new ArrayList<>();
    private static boolean IsChanged = true;

    public static void register(IGemstone gemstone) {
        GemstoneMap.put(gemstone.getRegisterName(), gemstone);
        IsChanged = true;
    }

    @Nullable
    public static IGemstone getGemstone(String registerName) {
        if (GemstoneMap.containsKey(registerName)) {
            return GemstoneMap.get(registerName);
        }
        return null;
    }

    public static boolean hasGemstone(String registerName) {
        return GemstoneMap.containsKey(registerName);
    }

    public static List<String> getAllGemstoneNames() {
        if (IsChanged) {
            genNamesCache();
        }
        return NamesCache;
    }

    private static void genNamesCache() {
        NamesCache = new ArrayList<>(GemstoneMap.keySet());
        IsChanged = false;
    }

}
