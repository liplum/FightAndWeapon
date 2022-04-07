package net.liplum.api.registeies;

import net.liplum.api.weapon.Cast;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CastRegistry {
    private static final Map<String, Cast> CastMap = new HashMap<>();
    private static final Map<Integer, Cast> IDMap = new HashMap<>();

    private static ArrayList<String> NamesCache = new ArrayList<>();
    private static boolean IsChanged = true;

    @NotNull
    public static Cast register(@NotNull Cast cast) {
        CastMap.put(cast.getRegisterName(), cast);
        IDMap.put(cast.getID(), cast);
        return cast;
    }

    @Nullable
    public static Cast getCastOf(int ID) {
        return IDMap.get(ID);
    }

    @Nullable
    public static Cast getCastOf(String registerName) {
        return CastMap.get(registerName);
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
