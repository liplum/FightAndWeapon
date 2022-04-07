package net.liplum.api.registeies;

import net.liplum.FawMod;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.WeaponType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public final class WeaponTypeRegistry {
    private static final Map<String, WeaponType> WeaponTypesMap = new HashMap<>();
    private static ArrayList<String> NamesCache = new ArrayList<>();
    private static boolean IsChanged = true;

    /**
     * Mustn't register a Weapon Type named "all"
     */
    @NotNull
    @LongSupport
    public static WeaponType register(@NotNull WeaponType weapon) {
        String name = weapon.getRegisterName();
        if (WeaponTypesMap.containsKey(name)) {
            FawMod.Logger.warn("Weapon Type " + name + " has been already registered! Notice whether it override another one unexpectedly.");
        }
        WeaponTypesMap.put(name, weapon);
        return weapon;
    }

    @Nullable
    @LongSupport
    public static WeaponType getWeaponTypeOf(@NotNull String name) {
        return WeaponTypesMap.get(name);
    }

    @LongSupport
    @NotNull
    public static List<String> getAllWeaponTypeNames() {
        if (IsChanged) {
            genNamesCache();
        }
        return NamesCache;
    }

    private static void genNamesCache() {
        NamesCache = new ArrayList<>(WeaponTypesMap.keySet());
        IsChanged = false;
    }

    @NotNull
    @LongSupport
    public static Collection<WeaponType> getAllWeaponTypes() {
        return WeaponTypesMap.values();
    }
}
