package net.liplum.api.registeies;

import net.liplum.api.weapon.Cast;
import net.liplum.api.weapon.WeaponPart;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeaponPartRegistry {
    private static final Map<String, WeaponPart> WeaponPartMap = new HashMap<>();
    private static ArrayList<String> NamesCache = new ArrayList<>();
    private static boolean IsChanged = false;

    enum RegisterType {
        Has_Cast,
        No_Cast
    }

    @Nonnull
    public static WeaponPart register(@Nonnull WeaponPart weaponPart, @Nonnull RegisterType registerType) {
        switch (registerType) {
            case Has_Cast:
                CastRegistry.register(new Cast(weaponPart));
            case No_Cast:
                WeaponPartMap.put(weaponPart.getRegisterName(), weaponPart);
        }
        return weaponPart;
    }

    public static List<String> getAllWeaponPartNames() {
        if (IsChanged) {
            genNamesCache();
        }
        return NamesCache;
    }

    private static void genNamesCache() {
        NamesCache = new ArrayList<>(WeaponPartMap.keySet());
        IsChanged = false;
    }
}
