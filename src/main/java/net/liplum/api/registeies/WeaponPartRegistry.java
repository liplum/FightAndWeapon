package net.liplum.api.registeies;

import net.liplum.api.annotations.Developing;
import net.liplum.api.weapon.Cast;
import net.liplum.api.weapon.WeaponPart;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Developing
public final class WeaponPartRegistry {
    private static final Map<String, WeaponPart> WeaponPartMap = new HashMap<>();
    private static ArrayList<String> NamesCache = new ArrayList<>();
    private static ArrayList<WeaponPart> WeaponPartsCache = new ArrayList<>();
    private static boolean IsChanged = true;

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

    @Nullable
    public static WeaponPart getWeaponPart(@Nonnull String weaponPartName) {
        return WeaponPartMap.get(weaponPartName);
    }

    public static List<String> getAllWeaponPartNames() {
        if (IsChanged) {
            genCache();
        }
        return NamesCache;
    }

    public static List<WeaponPart> getAllWeaponParts() {
        if (IsChanged) {
            genCache();
        }
        return WeaponPartsCache;
    }

    private static void genCache() {
        NamesCache = new ArrayList<>(WeaponPartMap.keySet());
        WeaponPartsCache = new ArrayList<>(WeaponPartMap.values());
        IsChanged = false;
    }

    public enum RegisterType {
        Has_Cast,
        No_Cast
    }
}
