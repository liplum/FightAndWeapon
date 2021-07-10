package net.liplum.api.registeies;

import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponType;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class WeaponRegistry {
    private static final Map<WeaponType, Set<WeaponBaseItem<?>>> AllWeapons = new HashMap<>();

    @Nonnull
    public static WeaponBaseItem<?> register(@Nonnull WeaponBaseItem<?> weapon) {
        WeaponType weaponType = weapon.getWeaponType();
        if (AllWeapons.containsKey(weaponType)) {
            AllWeapons.get(weaponType).add(weapon);
            return weapon;
        }
        HashSet<WeaponBaseItem<?>> weapons = new HashSet<>();
        weapons.add(weapon);
        AllWeapons.put(weaponType, weapons);
        return weapon;
    }

    @Nonnull
    public static Set<WeaponBaseItem<?>> getWeaponsOf(@Nonnull WeaponType type) {
        Set<WeaponBaseItem<?>> weapons = AllWeapons.get(type);
        if (weapons == null) {
            return new HashSet<>();
        }
        return weapons;
    }
}
