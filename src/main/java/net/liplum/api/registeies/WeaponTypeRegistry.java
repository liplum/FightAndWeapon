package net.liplum.api.registeies;

import net.liplum.FawMod;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.WeaponType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class WeaponTypeRegistry {
    private static final Map<String, WeaponType> WeaponTypesMap = new HashMap<>();
    private static ArrayList<String> NamesCache = new ArrayList<>();
    private static boolean IsChanged = true;

    /**
     * Mustn't register a Weapon Type named "all"
     */
    @Nonnull
    @LongSupport
    public static WeaponType register(@Nonnull WeaponType weapon) {
        String name = weapon.getRegisterName();
        if (WeaponTypesMap.containsKey(name)) {
            FawMod.Logger.warn("Weapon Type " + name + " has been already registered! Notice whether it override another one unexpectedly.");
        }
        WeaponTypesMap.put(name, weapon);
        return weapon;
    }

    @Nullable
    @LongSupport
    public static WeaponType getWeaponTypeOf(@Nonnull String name) {
        return WeaponTypesMap.get(name);
    }

    @LongSupport
    @Nonnull
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
}
