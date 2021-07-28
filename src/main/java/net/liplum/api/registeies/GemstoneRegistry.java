package net.liplum.api.registeies;

import net.liplum.FawMod;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.IGemstone;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LongSupport
public final class GemstoneRegistry {
    private static final Map<String, IGemstone> GemstoneMap = new HashMap<>();
    private static ArrayList<String> NamesCache = new ArrayList<>();
    private static boolean IsChanged = true;

    @LongSupport
    public static void register(IGemstone gemstone) {
        String name = gemstone.getRegisterName();
        if(GemstoneMap.containsKey(name)){
            FawMod.Logger.warn("Gemstone "+name+" has been already registered! Notice whether it override another one unexpectedly.");
        }
        GemstoneMap.put(name, gemstone);
        IsChanged = true;
    }

    @LongSupport
    @Nullable
    public static IGemstone getGemstone(String registerName) {
        if (GemstoneMap.containsKey(registerName)) {
            return GemstoneMap.get(registerName);
        }
        return null;
    }

    @LongSupport
    public static boolean hasGemstone(String registerName) {
        return GemstoneMap.containsKey(registerName);
    }

    @LongSupport
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
