package net.liplum.api.registeies;

import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class WeaponRegistry {
    private static final Map<WeaponType, Set<WeaponBaseItem<?>>> AllWeapons = new HashMap<>();

    @Nonnull
    public static WeaponBaseItem<?> register(@Nonnull WeaponType type, @Nonnull WeaponBaseItem<?> weapon) {
        if (AllWeapons.containsKey(type)) {
            AllWeapons.get(type).add(weapon);
            return weapon;
        }
        HashSet<WeaponBaseItem<?>> weapons = new HashSet<>();
        weapons.add(weapon);
        AllWeapons.put(type, weapons);
        return weapon;
    }

    @Nullable
    public static Set<WeaponBaseItem<?>> getWeaponOf(@Nonnull WeaponType type) {
        return AllWeapons.get(type);
    }
}
