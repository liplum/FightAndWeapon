package net.liplum.api.registeies;

import net.liplum.lib.items.WeaponBaseItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public final class WeaponRegistry {
    private static final Map<String, WeaponBaseItem<?>> AllWeapons = new HashMap<>();

    @Nonnull
    public static WeaponBaseItem<?> register(@Nonnull String name, @Nonnull WeaponBaseItem<?> weapon) {
        AllWeapons.put(name, weapon);
        return weapon;
    }

    @Nullable
    public static WeaponBaseItem<?> getWeaponOf(@Nonnull String name) {
        if (AllWeapons.containsKey(name)) {
            return AllWeapons.get(name);
        }
        return null;
    }
}
