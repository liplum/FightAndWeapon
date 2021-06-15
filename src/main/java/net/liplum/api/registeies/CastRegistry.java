package net.liplum.api.registeies;

import net.liplum.api.weapon.Cast;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CastRegistry {
    private static final Map<String, Cast> CastMap = new HashMap<>();
    private static ArrayList<String> NamesCache = new ArrayList<>();
    private static boolean IsChanged = false;
    @Nonnull
    public static Cast register(@Nonnull Cast cast) {
        CastMap.put(cast.getRegisterName(), cast);
        return cast;
    }
    public static List<String> getAllCastNames() {
        if (IsChanged) {
            genNamesCache();
        }
        return NamesCache;
    }

    private static void genNamesCache() {
        NamesCache = new ArrayList<>(CastMap.keySet());
        IsChanged = false;
    }
}
