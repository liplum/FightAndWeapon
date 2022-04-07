package net.liplum.api.registeies;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class WeaponRegistry {
    private static final Map<WeaponType, Set<WeaponBaseItem>> AllWeapons = new HashMap<>();

    @NotNull
    @LongSupport
    public static WeaponBaseItem register(@NotNull WeaponBaseItem weapon) {
        WeaponType weaponType = weapon.getWeaponType();
        if (AllWeapons.containsKey(weaponType)) {
            AllWeapons.get(weaponType).add(weapon);
            return weapon;
        }
        HashSet<WeaponBaseItem> weapons = new HashSet<>();
        weapons.add(weapon);
        AllWeapons.put(weaponType, weapons);
        return weapon;
    }

    @NotNull
    @LongSupport
    public static Set<WeaponBaseItem> getWeaponsOf(@NotNull WeaponType type) {
        Set<WeaponBaseItem> weapons = AllWeapons.get(type);
        if (weapons == null) {
            return new HashSet<>();
        }
        return weapons;
    }
}
