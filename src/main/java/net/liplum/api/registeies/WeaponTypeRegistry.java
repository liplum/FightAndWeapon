package net.liplum.api.registeies;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.WeaponType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public final class WeaponTypeRegistry {
    private static final Map<String, WeaponType> WeaponTypesMap = new HashMap<>();

    @Nonnull
    @LongSupport
    public static WeaponType register(@Nonnull WeaponType weapon) {
        WeaponTypesMap.put(weapon.getRegisterName(), weapon);
        return weapon;
    }

    @Nullable
    @LongSupport
    public static WeaponType getWeaponTypeOf(@Nonnull String name) {
        return WeaponTypesMap.get(name);
    }
}
